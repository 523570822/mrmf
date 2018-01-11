package com.mrmf.entity.kaoqin;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.osg.entity.DataEntity;

/**
 * 排班表
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class KPaiban extends DataEntity {
	private String organId; // 公司id
	private String staffId; // 员工id
	private int yearmonth;  // 排班年月，如201605
	private String day01;   // 班次id
	private String day02;
	private String day03;
	private String day04;
	private String day05;
	private String day06;
	private String day07;
	private String day08;
	private String day09;
	private String day10;
	private String day11;
	private String day12;
	private String day13;
	private String day14;
	private String day15;
	private String day16;
	private String day17;
	private String day18;
	private String day19;
	private String day20;
	private String day21;
	private String day22;
	private String day23;
	private String day24;
	private String day25;
	private String day26;
	private String day27;
	private String day28;
	private String day29;
	private String day30;
	private String day31;

	private int day01State;     // 考勤状态, 0未考勤；1已上班签到；2已下班签到；
	private int day02State;
	private int day03State;
	private int day04State;
	private int day05State;   
	private int day06State;
	private int day07State;
	private int day08State;
	private int day09State;
	private int day10State;
	private int day11State;
	private int day12State;
	private int day13State;
	private int day14State;
	private int day15State;
	private int day16State;
	private int day17State;
	private int day18State;
	private int day19State;
	private int day20State;
	private int day21State;
	private int day22State;
	private int day23State;
	private int day24State;
	private int day25State;
	private int day26State;
	private int day27State;
	private int day28State;
	private int day29State;
	private int day30State;
	private int day31State;
	/* 
	 * 开始时间
	 * 结束时间
	 * 初始值为员工的创建时间
	 * 最后一次打卡的时间  
	 */
	private Date startTime; //员工的创建时间
	private Date endTime;   //最后一天最后一天的上一天的

	
	/**
	 * 非存储字段
	 */
	private String staffName;
	private String day01B;   // 班次id
	private String day02B;
	private String day03B;
	private String day04B;
	private String day05B;
	private String day06B;
	private String day07B;
	private String day08B;
	private String day09B;
	private String day10B;
	private String day11B;
	private String day12B;
	private String day13B;
	private String day14B;
	private String day15B;
	private String day16B;
	private String day17B;
	private String day18B;
	private String day19B;
	private String day20B;
	private String day21B;
	private String day22B;
	private String day23B;
	private String day24B;
	private String day25B;
	private String day26B;
	private String day27B;
	private String day28B;
	private String day29B;
	private String day30B;
	private String day31B;
	
	
	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public int getYearmonth() {
		return yearmonth;
	}

	public void setYearmonth(int yearmonth) {
		this.yearmonth = yearmonth;
	}

	public String getDay01() {
		return day01;
	}

	public void setDay01(String day01) {
		this.day01 = day01;
	}

	public String getDay02() {
		return day02;
	}

	public void setDay02(String day02) {
		this.day02 = day02;
	}

	public String getDay03() {
		return day03;
	}

	public void setDay03(String day03) {
		this.day03 = day03;
	}

	public String getDay04() {
		return day04;
	}

	public void setDay04(String day04) {
		this.day04 = day04;
	}

	public String getDay05() {
		return day05;
	}

	public void setDay05(String day05) {
		this.day05 = day05;
	}

	public String getDay06() {
		return day06;
	}

	public void setDay06(String day06) {
		this.day06 = day06;
	}

	public String getDay07() {
		return day07;
	}

	public void setDay07(String day07) {
		this.day07 = day07;
	}

	public String getDay08() {
		return day08;
	}

	public void setDay08(String day08) {
		this.day08 = day08;
	}

	public String getDay09() {
		return day09;
	}

	public void setDay09(String day09) {
		this.day09 = day09;
	}

	public String getDay10() {
		return day10;
	}

	public void setDay10(String day10) {
		this.day10 = day10;
	}

	public String getDay11() {
		return day11;
	}

	public void setDay11(String day11) {
		this.day11 = day11;
	}

	public String getDay12() {
		return day12;
	}

	public void setDay12(String day12) {
		this.day12 = day12;
	}

	public String getDay13() {
		return day13;
	}

	public void setDay13(String day13) {
		this.day13 = day13;
	}

	public String getDay14() {
		return day14;
	}

	public void setDay14(String day14) {
		this.day14 = day14;
	}

	public String getDay15() {
		return day15;
	}

	public void setDay15(String day15) {
		this.day15 = day15;
	}

	public String getDay16() {
		return day16;
	}

	public void setDay16(String day16) {
		this.day16 = day16;
	}

	public String getDay17() {
		return day17;
	}

	public void setDay17(String day17) {
		this.day17 = day17;
	}

	public String getDay18() {
		return day18;
	}

	public void setDay18(String day18) {
		this.day18 = day18;
	}

	public String getDay19() {
		return day19;
	}

	public void setDay19(String day19) {
		this.day19 = day19;
	}

	public String getDay20() {
		return day20;
	}

	public void setDay20(String day20) {
		this.day20 = day20;
	}

	public String getDay21() {
		return day21;
	}

	public void setDay21(String day21) {
		this.day21 = day21;
	}

	public String getDay22() {
		return day22;
	}

	public void setDay22(String day22) {
		this.day22 = day22;
	}

	public String getDay23() {
		return day23;
	}

	public void setDay23(String day23) {
		this.day23 = day23;
	}

	public String getDay24() {
		return day24;
	}

	public void setDay24(String day24) {
		this.day24 = day24;
	}

	public String getDay25() {
		return day25;
	}

	public void setDay25(String day25) {
		this.day25 = day25;
	}

	public String getDay26() {
		return day26;
	}

	public void setDay26(String day26) {
		this.day26 = day26;
	}

	public String getDay27() {
		return day27;
	}

	public void setDay27(String day27) {
		this.day27 = day27;
	}

	public String getDay28() {
		return day28;
	}

	public void setDay28(String day28) {
		this.day28 = day28;
	}

	public String getDay29() {
		return day29;
	}

	public void setDay29(String day29) {
		this.day29 = day29;
	}

	public String getDay30() {
		return day30;
	}

	public void setDay30(String day30) {
		this.day30 = day30;
	}

	public String getDay31() {
		return day31;
	}

	public void setDay31(String day31) {
		this.day31 = day31;
	}

	public int getDay01State() {
		return day01State;
	}

	public void setDay01State(int day01State) {
		this.day01State = day01State;
	}

	public int getDay02State() {
		return day02State;
	}

	public void setDay02State(int day02State) {
		this.day02State = day02State;
	}

	public int getDay03State() {
		return day03State;
	}

	public void setDay03State(int day03State) {
		this.day03State = day03State;
	}

	public int getDay04State() {
		return day04State;
	}

	public void setDay04State(int day04State) {
		this.day04State = day04State;
	}

	public int getDay05State() {
		return day05State;
	}

	public void setDay05State(int day05State) {
		this.day05State = day05State;
	}

	public int getDay06State() {
		return day06State;
	}

	public void setDay06State(int day06State) {
		this.day06State = day06State;
	}

	public int getDay07State() {
		return day07State;
	}

	public void setDay07State(int day07State) {
		this.day07State = day07State;
	}

	public int getDay08State() {
		return day08State;
	}

	public void setDay08State(int day08State) {
		this.day08State = day08State;
	}

	public int getDay09State() {
		return day09State;
	}

	public void setDay09State(int day09State) {
		this.day09State = day09State;
	}

	public int getDay10State() {
		return day10State;
	}

	public void setDay10State(int day10State) {
		this.day10State = day10State;
	}

	public int getDay11State() {
		return day11State;
	}

	public void setDay11State(int day11State) {
		this.day11State = day11State;
	}

	public int getDay12State() {
		return day12State;
	}

	public void setDay12State(int day12State) {
		this.day12State = day12State;
	}

	public int getDay13State() {
		return day13State;
	}

	public void setDay13State(int day13State) {
		this.day13State = day13State;
	}

	public int getDay14State() {
		return day14State;
	}

	public void setDay14State(int day14State) {
		this.day14State = day14State;
	}

	public int getDay15State() {
		return day15State;
	}

	public void setDay15State(int day15State) {
		this.day15State = day15State;
	}

	public int getDay16State() {
		return day16State;
	}

	public void setDay16State(int day16State) {
		this.day16State = day16State;
	}

	public int getDay17State() {
		return day17State;
	}

	public void setDay17State(int day17State) {
		this.day17State = day17State;
	}

	public int getDay18State() {
		return day18State;
	}

	public void setDay18State(int day18State) {
		this.day18State = day18State;
	}

	public int getDay19State() {
		return day19State;
	}

	public void setDay19State(int day19State) {
		this.day19State = day19State;
	}

	public int getDay20State() {
		return day20State;
	}

	public void setDay20State(int day20State) {
		this.day20State = day20State;
	}

	public int getDay21State() {
		return day21State;
	}

	public void setDay21State(int day21State) {
		this.day21State = day21State;
	}

	public int getDay22State() {
		return day22State;
	}

	public void setDay22State(int day22State) {
		this.day22State = day22State;
	}

	public int getDay23State() {
		return day23State;
	}

	public void setDay23State(int day23State) {
		this.day23State = day23State;
	}

	public int getDay24State() {
		return day24State;
	}

	public void setDay24State(int day24State) {
		this.day24State = day24State;
	}

	public int getDay25State() {
		return day25State;
	}

	public void setDay25State(int day25State) {
		this.day25State = day25State;
	}

	public int getDay26State() {
		return day26State;
	}

	public void setDay26State(int day26State) {
		this.day26State = day26State;
	}

	public int getDay27State() {
		return day27State;
	}

	public void setDay27State(int day27State) {
		this.day27State = day27State;
	}

	public int getDay28State() {
		return day28State;
	}

	public void setDay28State(int day28State) {
		this.day28State = day28State;
	}

	public int getDay29State() {
		return day29State;
	}

	public void setDay29State(int day29State) {
		this.day29State = day29State;
	}

	public int getDay30State() {
		return day30State;
	}

	public void setDay30State(int day30State) {
		this.day30State = day30State;
	}

	public int getDay31State() {
		return day31State;
	}

	public void setDay31State(int day31State) {
		this.day31State = day31State;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	public String getDay01B() {
		return day01B;
	}

	public void setDay01B(String day01b) {
		day01B = day01b;
	}

	public String getDay02B() {
		return day02B;
	}

	public void setDay02B(String day02b) {
		day02B = day02b;
	}

	public String getDay03B() {
		return day03B;
	}

	public void setDay03B(String day03b) {
		day03B = day03b;
	}

	public String getDay04B() {
		return day04B;
	}

	public void setDay04B(String day04b) {
		day04B = day04b;
	}

	public String getDay05B() {
		return day05B;
	}

	public void setDay05B(String day05b) {
		day05B = day05b;
	}

	public String getDay06B() {
		return day06B;
	}

	public void setDay06B(String day06b) {
		day06B = day06b;
	}

	public String getDay07B() {
		return day07B;
	}

	public void setDay07B(String day07b) {
		day07B = day07b;
	}

	public String getDay08B() {
		return day08B;
	}

	public void setDay08B(String day08b) {
		day08B = day08b;
	}

	public String getDay09B() {
		return day09B;
	}

	public void setDay09B(String day09b) {
		day09B = day09b;
	}

	public String getDay10B() {
		return day10B;
	}

	public void setDay10B(String day10b) {
		day10B = day10b;
	}

	public String getDay11B() {
		return day11B;
	}

	public void setDay11B(String day11b) {
		day11B = day11b;
	}

	public String getDay12B() {
		return day12B;
	}

	public void setDay12B(String day12b) {
		day12B = day12b;
	}

	public String getDay13B() {
		return day13B;
	}

	public void setDay13B(String day13b) {
		day13B = day13b;
	}

	public String getDay14B() {
		return day14B;
	}

	public void setDay14B(String day14b) {
		day14B = day14b;
	}

	public String getDay15B() {
		return day15B;
	}

	public void setDay15B(String day15b) {
		day15B = day15b;
	}

	public String getDay16B() {
		return day16B;
	}

	public void setDay16B(String day16b) {
		day16B = day16b;
	}

	public String getDay17B() {
		return day17B;
	}

	public void setDay17B(String day17b) {
		day17B = day17b;
	}

	public String getDay18B() {
		return day18B;
	}

	public void setDay18B(String day18b) {
		day18B = day18b;
	}

	public String getDay19B() {
		return day19B;
	}

	public void setDay19B(String day19b) {
		day19B = day19b;
	}

	public String getDay20B() {
		return day20B;
	}

	public void setDay20B(String day20b) {
		day20B = day20b;
	}

	public String getDay21B() {
		return day21B;
	}

	public void setDay21B(String day21b) {
		day21B = day21b;
	}

	public String getDay22B() {
		return day22B;
	}

	public void setDay22B(String day22b) {
		day22B = day22b;
	}

	public String getDay23B() {
		return day23B;
	}

	public void setDay23B(String day23b) {
		day23B = day23b;
	}

	public String getDay24B() {
		return day24B;
	}

	public void setDay24B(String day24b) {
		day24B = day24b;
	}

	public String getDay25B() {
		return day25B;
	}

	public void setDay25B(String day25b) {
		day25B = day25b;
	}

	public String getDay26B() {
		return day26B;
	}

	public void setDay26B(String day26b) {
		day26B = day26b;
	}

	public String getDay27B() {
		return day27B;
	}

	public void setDay27B(String day27b) {
		day27B = day27b;
	}

	public String getDay28B() {
		return day28B;
	}

	public void setDay28B(String day28b) {
		day28B = day28b;
	}

	public String getDay29B() {
		return day29B;
	}

	public void setDay29B(String day29b) {
		day29B = day29b;
	}

	public String getDay30B() {
		return day30B;
	}

	public void setDay30B(String day30b) {
		day30B = day30b;
	}

	public String getDay31B() {
		return day31B;
	}

	public void setDay31B(String day31b) {
		day31B = day31b;
	}
	//----------------------------------
}
