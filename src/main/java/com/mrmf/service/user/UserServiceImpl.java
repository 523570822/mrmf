package com.mrmf.service.user;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mrmf.entity.User;
import com.mrmf.entity.user.Bigsort;
import com.mrmf.entity.user.Smallsort;
import com.mrmf.entity.user.Usercard;
import com.mrmf.entity.user.Userincard;
import com.mrmf.entity.user.Userpart;
import com.mrmf.entity.user.Usersort;
import com.mrmf.service.user.smallsort.SmallsortService;
import com.mrmf.service.user.usersort.UsersortService;
import com.osg.entity.FlipInfo;
import com.osg.entity.ReturnStatus;
import com.osg.framework.BaseException;
import com.osg.framework.mongodb.EMongoTemplate;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.OSSFileUtil;
import com.osg.framework.util.StringUtils;

@Service("memberService")
public class UserServiceImpl implements UserService {

	@Autowired
	private EMongoTemplate mongoTemplate;

	@Autowired
	private UsersortService usersortService;

	@Autowired
	private SmallsortService smallsortService;

	@Override
	public User queryById(String userId) throws BaseException {
		return mongoTemplate.findById(userId, User.class);
	}

	@Override
	public FlipInfo<Bigsort> query(FlipInfo<Bigsort> fpi) throws BaseException {
		mongoTemplate.findByPage(null, fpi, Bigsort.class);
		return fpi;
	}

	@Override
	public ReturnStatus importUser(String organId, String fileId) {
		ReturnStatus status;
		Workbook workbook = null;
		InputStream is = null;
		try {
			// 尝试xlsx格式类型读取文件.try read file:xlsx
			is = OSSFileUtil.readFileInputStream(fileId, OSSFileUtil.privBucketName);
			workbook = new XSSFWorkbook(is);
		} catch (Throwable e) {
			// 不是xlsx格式类型文件,转换xls格式读取
			try {
				if (is == null) {
					is = OSSFileUtil.readFileInputStream(fileId, OSSFileUtil.privBucketName);
				}
				if (is != null) {
					workbook = new HSSFWorkbook(is);
				} else {
					status = new ReturnStatus(false);
					status.setMessage("文件格式错误，请上传xls或xlsx文件");
					return status;
				}
			} catch (Throwable e1) {
				status = new ReturnStatus(false);
				status.setMessage("文件格式错误，请上传xls或xlsx文件");
				return status;
			}
		}

		Map<String, Usersort> membersortMap = new HashMap<>();
		List<Usersort> membersorts;
		try {
			membersorts = usersortService.findAll(organId);
		} catch (BaseException e1) {
			return new ReturnStatus(false, e1.getMessage());
		}
		for (Usersort membersort : membersorts) {
			// 刨除非会员
			if (!"1000".equals(membersort.getFlag1())) {
				membersortMap.put(membersort.get_id(), membersort);
				membersortMap.put(membersort.getName1(), membersort);
			}
		}

		Map<String, Smallsort> smallsortMap = new HashMap<>();
		List<Smallsort> smallsorts;
		try {
			smallsorts = smallsortService.findAll(organId);
		} catch (BaseException e1) {
			return new ReturnStatus(false, e1.getMessage());
		}
		for (Smallsort smallsort : smallsorts) {
			smallsortMap.put(smallsort.get_id(), smallsort);
		}

		int userCount = 0;
		int usercardCount = 0;
		int userIncardCount = 0;
		int userpartCount = 0;

		StringBuilder errorBuf = new StringBuilder(); // 会员信息导入错误信息字符串
		Map<String, User> userMap = new HashMap<>();
		// Read the Sheet
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet xssfSheet = workbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			// Read the Row，忽略第一行（标题行）
			for (int rowNum = 2; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				Row row = xssfSheet.getRow(rowNum);
				if (row != null) {
					String name = getCellValue(row, 0);
					String sex = getCellValue(row, 1);
					String birthdayStr = getCellValue(row, 2);
					String place = getCellValue(row, 3);
					String phone = getCellValue(row, 4);
					String love = getCellValue(row, 5);
					String cardno = getCellValue(row, 6);
					String id_2 = getCellValue(row, 7);
					String passwd = getCellValue(row, 8);
					String membersortStr = getCellValue(row, 9);
					String money4Str = getCellValue(row, 10);
					String shengcishuStr = getCellValue(row, 11);

					double money4 = 0;
					int shengcishu = 0;
					Usersort usersort = null;

					if (StringUtils.isEmpty(name)) {
						errorBuf.append("[错误]姓名不能为空，行(").append(rowNum + 1).append(")<br>");
						continue;
					}
					if (StringUtils.isEmpty(phone)) {
						errorBuf.append("[错误]电话不能为空，行(").append(rowNum + 1).append(")<br>");
						continue;
					}
					if (StringUtils.isEmpty(id_2)) {
						errorBuf.append("[错误]会员卡号不能为空，行(").append(rowNum + 1).append(")<br>");
						continue;
					}
					if (StringUtils.isEmpty(membersortStr)) {
						errorBuf.append("[错误]会员类别不能为空，行(").append(rowNum + 1).append(")<br>");
						continue;
					}
					usersort = membersortMap.get(membersortStr);
					if (usersort == null) {
						errorBuf.append("[错误]会员类别\"" + membersortStr + "\"不存在，行(").append(rowNum + 1).append(")<br>");
						continue;
					}
					if ("1003".equals(usersort.getFlag1())) { // 次数卡
						if (!StringUtils.isEmpty(shengcishuStr))
							try {
								shengcishu = Integer.parseInt(shengcishuStr);
							} catch (Throwable t) {
								errorBuf.append("[错误]剩余次数格式错误\"" + shengcishuStr + "\"，行(").append(rowNum + 1)
										.append(")<br>");
								continue;
							}
						else
							shengcishu = usersort.getCishu();
					}
					if ("1003".equals(usersort.getFlag1())) { // 存钱打折卡
						if (!StringUtils.isEmpty(money4Str)) {
							try {
								money4 = Float.parseFloat(money4Str);
							} catch (Throwable t) {
								errorBuf.append("[错误]卡余额格式错误\"" + money4Str + "\"，行(").append(rowNum + 1)
										.append(")<br>");
								continue;
							}
						} else
							money4 = usersort.getMoney();
					}

					// 卡表面号为空，则默认为会员卡号
					if (StringUtils.isEmpty(cardno))
						cardno = id_2;
					if (StringUtils.isEmpty(passwd))
						passwd = "111111";
					Date birthday = null;
					if (!StringUtils.isEmpty(birthdayStr)) {
						try {
							birthday = DateUtil.parse(birthdayStr);
						} catch (ParseException e) {
							errorBuf.append("[错误]会员生日格式错误，行(").append(rowNum + 1).append(")<br>");
							continue;
						}
					}

					/**
					 * -------------------生成用户信息开始
					 */
					User bean = mongoTemplate.findOne(Query.query(Criteria.where("phone").is(phone)), User.class);
					if (bean == null) {
						bean = new User();
						bean.setIdIfNew();
						bean.setCreateTimeIfNew();

						bean.setImportId(organId);
						bean.setName(name);

						bean.setSex(sex);

						bean.setBirthday(birthday);
						bean.setPlace(place);
						bean.setPhone(phone);
						bean.setLove(love);

						mongoTemplate.save(bean);
						userCount++;
					} else {
						errorBuf.append("[信息]已存在相同手机号会员信息，已进行合并，行(").append(rowNum + 1).append(")<br>");
					}

					/**
					 * ====================生成用户信息结束
					 */

					Usercard bean2 = null;

					/**
					 * -------------------生成会员关系开始
					 */
					Criteria criteria = new Criteria();
					criteria.andOperator(Criteria.where("userId").is(bean.get_id()),
							Criteria.where("organId").is(organId));
					bean2 = mongoTemplate.findOne(Query.query(criteria), Usercard.class);
					if (bean2 == null) {
						// 生成usercard
						bean2 = new Usercard();
						bean2.setIdIfNew();
						bean2.setCreateTimeIfNew();

						bean2.setOrganId(organId);
						bean2.setUserId(bean.get_id());
						bean2.setComeday(DateUtil.currentDate());
						bean2.setLastcomeday(bean2.getComeday());
						bean2.setCome_num(1);
						bean2.setJifen(0);
						bean2.setPasswd(passwd);
						bean2.setCardno(cardno);

						mongoTemplate.save(bean2);
						usercardCount++;
					} else {
						errorBuf.append("[信息]店铺已存在与此会员关联，行(").append(rowNum + 1).append(")<br>");
					}

					/**
					 * ====================生成会员关系结束
					 */

					/**
					 * -------------------生成会员主卡信息开始
					 */
					Userincard bean3 = null;
					bean3 = new Userincard();
					bean3.setIdIfNew();
					bean3.setCreateTimeIfNew();

					bean3.setOrganId(organId);
					bean3.setUserId(bean.get_id());
					bean3.setCardId(bean2.get_id());

					bean3.setMembersort(usersort.get_id());
					bean3.setZhekou(usersort.getZhekou());
					bean3.setFlag1(usersort.getFlag1());
					bean3.setSmallsort(usersort.getName2());
					Smallsort sm = smallsortMap.get(bean3.getSmallsort());
					if (sm != null)
						bean3.setBigsort(sm.getBigcode());

					bean3.setAllcishu(usersort.getCishu());
					bean3.setShengcishu(shengcishu);
					bean3.setDanci_money(usersort.getDanci_money());
					bean3.setMoney_leiji(money4);
					bean3.setMoney_qian(0);
					bean3.setXu_cishu(0);
					bean3.setMoney4(money4);
					bean3.setId_2(id_2);
					bean3.setCardno(cardno);

					bean3.setDelete_flag(false);
					mongoTemplate.save(bean3);
					userIncardCount++;
					/**
					 * ====================生成会员主卡信息结束
					 */

					/**
					 * -------------------生成会员消费记录信息开始
					 */
					Userpart bean4 = new Userpart();
					bean4.setIdIfNew();
					bean4.setCreateTimeIfNew();

					bean4.setName(name);
					bean4.setType(0); // 办卡类型
					bean4.setFlag2(true); // 是否结账 true
					bean4.setGuazhang_flag(false);
					bean4.setDelete_flag(false);
					bean4.setOrganId(organId);
					bean4.setUserId(bean.get_id());

					bean4.setCardId(bean2.get_id());
					bean4.setIncardId(bean3.get_id());

					bean4.setMembersort(usersort.get_id());

					bean4.setBigsort(bean3.getBigsort());
					bean4.setSmallsort(bean3.getSmallsort());

					bean4.setAllcishu(bean3.getAllcishu());
					bean4.setShengcishu(bean3.getShengcishu());
					bean4.setMoney_leiji(bean3.getMoney_leiji());

					bean4.setMoney_qian(0);
					bean4.setXu_cishu(0);

					bean4.setMoney1(usersort.getMoney());
					bean4.setMoney2(bean4.getMoney1());
					bean4.setMoney3(0);
					bean4.setMoney4(bean4.getMoney1());
					bean4.setMoney5(0);
					bean4.setMoney6(bean3.getZhekou());
					bean4.setFlag1(false);

					bean4.setMoney_cash(bean4.getMoney1());
					bean4.setMiandan(false);
					bean4.setLaibin_flag(false);

					bean4.setId_2(id_2);
					bean4.setCardno(cardno);

					bean4.setSex(sex);

					mongoTemplate.save(bean4);
					userpartCount++;
					/**
					 * ====================生成会员消费记录信息结束
					 */
				}
			}
		}

		errorBuf.append("成功导入会员基本信息:" + userCount + "，会员与公司关联:" + usercardCount + "，会员卡:" + userIncardCount + "<br>");

		if (errorBuf.length() > 0) {
			status = new ReturnStatus(false);
			status.setMessage(errorBuf.toString());
		} else {
			status = new ReturnStatus(true);
		}
		return status;
	}

	DecimalFormat df = new DecimalFormat("#");

	protected String getCellValue(Row row, int celNum) {
		if (row == null || celNum < 0) {
			return null;
		}
		Cell cell = row.getCell(celNum);
		if (cell != null) {
			String value = null;

			try {
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_NUMERIC: // 数字
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						// 如果是date类型则 ，获取该cell的date值
						value = DateUtil.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
								DateUtil.YMDHMS_PATTERN);
					} else { // 纯数字
						value = df.format(cell.getNumericCellValue()).toString();
					}
					break;
				case HSSFCell.CELL_TYPE_STRING: // 字符串
					value = cell.getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
					if (cell.getBooleanCellValue()) {
						value = "是";
					} else {
						value = "否";
					}
					break;
				case HSSFCell.CELL_TYPE_FORMULA: // 公式
					value = cell.getCellFormula();
					break;
				case HSSFCell.CELL_TYPE_BLANK: // 空值
					break;
				case HSSFCell.CELL_TYPE_ERROR: // 故障
					break;
				default:
					break;
				}
			} catch (Exception e) {
				value = cell.getStringCellValue();
			}

			if (StringUtils.isEmpty(value)) {
				value = null;
			} else {
				value = value.trim();
			}

			return value;
		} else
			return null;
	}
}
