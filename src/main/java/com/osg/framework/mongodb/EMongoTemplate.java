package com.osg.framework.mongodb;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.Mongo;
import com.osg.entity.FlipInfo;
import com.osg.entity.geo.GeoDistance;
import com.osg.framework.util.DateUtil;
import com.osg.framework.util.FlipPageInfo;
import com.osg.framework.util.StringUtils;

public class EMongoTemplate extends MongoTemplate {

	public EMongoTemplate(Mongo mongo, String databaseName) {
		super(mongo, databaseName);
	}

	public EMongoTemplate(MongoDbFactory mongoDbFactory) {
		super(mongoDbFactory);
	}

	public EMongoTemplate(MongoDbFactory mongoDbFactory, MongoConverter mongoConverter) {
		super(mongoDbFactory, mongoConverter);
	}

	public <T> List<T> find(Query query, Map param, Class<T> clazz) {
		String collectionName = getCollectionName(clazz);
		return this.findByPage(query, param, clazz, collectionName);
	}

	public <T> List<T> findByPage(Query query, Map param, Class<T> clazz, String collectionName) {
		if (query == null)
			query = new Query();
		param = FlipPageInfo.convertMap(param);
		processParams(query, null, param);
		return find(query, clazz, collectionName);
	}

	public <T> FlipInfo<T> findByPage(Query query, Criteria criteria, FlipInfo<T> pp, Class<T> clazz) {
		String collectionName = getCollectionName(clazz);
		return this.findByPage(query, criteria, pp, clazz, collectionName);
	}

	public <T> FlipInfo<T> findByPage(Query query, FlipInfo<T> pp, Class<T> clazz) {
		String collectionName = getCollectionName(clazz);
		return this.findByPage(query, pp, clazz, collectionName);
	}

	public <T> FlipInfo<T> findByPage(Query query, Criteria criteria, FlipInfo<T> pp, Class<T> clazz,
			String collectionName) {
		if (query == null)
			query = new Query();
		processParams(query, criteria, pp.getParams());
		Map<String, String> params = pp.getParams();
		if (params != null) {
			String sortField = params.get("sidx");
			String sortOrder = params.get("sord");
			if (!StringUtils.isEmpty(sortField)) {
				pp.setSortField(sortField);
				pp.setSortOrder(sortOrder);
			}
		}
		if (pp.isNeedTotal()) {
			int totalSize = new Long(count(query, collectionName)).intValue();
			pp.setTotal(totalSize);
		}
		int skip = pp.getFirstPageValue();
		int limit = pp.getSize();
		query.skip(skip).limit(limit);
		if (!StringUtils.isEmpty(pp.getSortField())) {
			Sort sort = null;
			List<String> sortFields = Arrays.asList(pp.getSortField().split(","));
			if (!StringUtils.isEmpty(pp.getSortOrder())) {
				if (pp.getSortOrder().equalsIgnoreCase(Direction.ASC.toString())) {
					sort = new Sort(Direction.ASC, sortFields);
				} else {
					sort = new Sort(Direction.DESC, sortFields);
				}
			} else {
				sort = new Sort(Direction.ASC, sortFields);
			}
			if (sort != null) {
				query.with(sort);
			}
		}
		List<T> result = find(query, clazz, collectionName);
		pp.setData(result);
		return pp;
	}

	public <T> FlipInfo<T> findByPage(Query query, FlipInfo<T> pp, Class<T> clazz, String collectionName) {
		return findByPage(query, null, pp, clazz, collectionName);
	}

	/**
	 * 地理位置查询
	 * 
	 * @param longitude
	 * @param latitude
	 * @param maxDistance
	 *            -1表示不限地理位置
	 * @param query
	 * @param pp
	 * @param clazz
	 * @return
	 */
	public <T> FlipInfo<T> findByPageGeo(double longitude, double latitude, double maxDistance, Query query,
			FlipInfo<T> pp, Class<T> clazz) {
		String collectionName = getCollectionName(clazz);
		return this.findByPageGeo(longitude, latitude, maxDistance, query, pp, clazz, collectionName);
	}

	/**
	 * 地理位置查询
	 * 
	 * @param longitude
	 * @param latitude
	 * @param maxDistance
	 *            -1表示不限地理位置， 单位：公里
	 * @param query
	 * @param pp
	 * @param clazz
	 * @param collectionName
	 * @return
	 */
	public <T> FlipInfo<T> findByPageGeo(double longitude, double latitude, double maxDistance, Query query,
			FlipInfo<T> pp, Class<T> clazz, String collectionName) {
		if (query == null)
			query = new Query();
		processParams(query, null, pp.getParams());
		int skip = pp.getFirstPageValue();
		int limit = pp.getSize();
		// query.skip(skip).limit(limit);
		if (!StringUtils.isEmpty(pp.getSortField())) {
			List<String> sortFields = Arrays.asList(pp.getSortField().split(","));
			Sort sort = null;
			if (!StringUtils.isEmpty(pp.getSortOrder())) {
				if (pp.getSortOrder().equalsIgnoreCase(Direction.ASC.toString())) {
					sort = new Sort(Direction.ASC, sortFields);
				} else {
					sort = new Sort(Direction.DESC, sortFields);
				}
			} else {
				sort = new Sort(Direction.ASC, sortFields);
			}
			if (sort != null) {
				query.with(sort);
			}
		}

		NearQuery nearQuery;
		if (maxDistance > 0) {
			nearQuery = NearQuery.near(longitude, latitude).spherical(true).maxDistance(maxDistance * 1000 / 6378137)
					.in(Metrics.KILOMETERS).query(query).skip(skip).num(pp.getPage() * pp.getSize());
			if (pp.isNeedTotal()) {
				int totalSize = new Long(count(query, collectionName)).intValue();
				pp.setTotal(totalSize);
			}
		} else {
			nearQuery = NearQuery.near(longitude, latitude).spherical(true).in(Metrics.KILOMETERS).query(query)
					.skip(skip).num(pp.getPage() * pp.getSize());
			if (pp.isNeedTotal()) {
				int totalSize = new Long(count(query, collectionName)).intValue();
				pp.setTotal(totalSize);
			}
		}
		if (pp.getTotal() > 0) {
			GeoResults<T> results = geoNear(nearQuery, clazz, collectionName);
			Iterator<GeoResult<T>> ite = results.iterator();
			GeoResult<T> result;
			T data;
			List<T> dataList = new ArrayList<T>();
			while (ite.hasNext()) {
				result = ite.next();
				data = result.getContent();
				((GeoDistance) data).setDistance(result.getDistance().getValue());
				dataList.add(data);
			}
			pp.setData(dataList);
		}
		return pp;
	}

	public void processParams(Query query, Criteria c, Map<String, String> params) {
		Criteria criteria = new Criteria();
		Criteria criteriaOr = new Criteria();
		if (params != null) {
			List<Criteria> conditionList = new ArrayList<Criteria>();
			List<Criteria> conditionListOr = new ArrayList<Criteria>();
			for (String k : params.keySet()) {
				if (!excludeKeyList.contains(k)) {
					String value = params.get(k);
					if (!StringUtils.isEmpty(value)) {
						String condition = "is";
						String key = k;
						String type = null;
						String[] tokens = key.split("\\:");
						String connector = null;// 连接符
						if (tokens.length == 2) {
							condition = tokens[0];
							key = tokens[1];
						}
						tokens = key.split("\\|");
						if (tokens.length == 2) {
							key = tokens[0];
							type = tokens[1];
							tokens = type.split("#");
							if (tokens.length == 2) {
								type = tokens[0];
								connector = tokens[1];
							}
						}
						if (condition.equalsIgnoreCase("ignore")) {
							continue;
						} else if (condition.equalsIgnoreCase("regex")) {
							if (connector != null) {
								if (connector.equalsIgnoreCase("or")) {
									conditionListOr.add(Criteria.where(key).regex(value));
								} else {
									conditionList.add(Criteria.where(key).regex(value));
								}
							} else {
								conditionList.add(Criteria.where(key).regex(value));
							}
						} else if (condition.equalsIgnoreCase("preregex")) {
							if (connector != null) {
								if (connector.equalsIgnoreCase("or")) {
									conditionListOr.add(Criteria.where(key).regex("^" + value + ".*$"));
								} else {
									conditionList.add(Criteria.where(key).regex("^" + value + ".*$"));
								}
							} else {
								conditionList.add(Criteria.where(key).regex("^" + value + ".*$"));
							}
						} else if (condition.equalsIgnoreCase("gt")) {
							if (connector != null) {
								if (connector.equalsIgnoreCase("or")) {
									conditionListOr.add(Criteria.where(key).gt(extractValue(type, value)));
								} else {
									conditionList.add(Criteria.where(key).gt(extractValue(type, value)));
								}
							} else {
								conditionList.add(Criteria.where(key).gt(extractValue(type, value)));
							}

						} else if (condition.equalsIgnoreCase("gte")) {
							if (connector != null) {
								if (connector.equalsIgnoreCase("or")) {
									conditionListOr.add(Criteria.where(key).gte(extractValue(type, value)));
								} else {
									conditionList.add(Criteria.where(key).gte(extractValue(type, value)));
								}
							} else {
								conditionList.add(Criteria.where(key).gte(extractValue(type, value)));
							}

						} else if (condition.equalsIgnoreCase("lt")) {
							if (connector != null) {
								if (connector.equalsIgnoreCase("or")) {
									conditionListOr.add(Criteria.where(key).lt(extractValue(type, value)));
								} else {
									conditionList.add(Criteria.where(key).lt(extractValue(type, value)));
								}
							} else {
								conditionList.add(Criteria.where(key).lt(extractValue(type, value)));
							}

						} else if (condition.equalsIgnoreCase("lte")) {
							if (connector != null) {
								if (connector.equalsIgnoreCase("or")) {
									conditionListOr.add(Criteria.where(key).lte(extractValue(type, value)));
								} else {
									conditionList.add(Criteria.where(key).lte(extractValue(type, value)));
								}
							} else {
								conditionList.add(Criteria.where(key).lte(extractValue(type, value)));
							}

						} else if (condition.equalsIgnoreCase("all")) {
							Object v = extractValue(type, value);
							if (v instanceof Collection) {
								if (connector != null) {
									if (connector.equalsIgnoreCase("or")) {
										conditionListOr.add(Criteria.where(key).all((Collection) v));
									} else {
										conditionList.add(Criteria.where(key).all((Collection) v));
									}
								} else {
									conditionList.add(Criteria.where(key).all((Collection) v));
								}

							} else {
								if (connector != null) {
									if (connector.equalsIgnoreCase("or")) {
										conditionListOr.add(Criteria.where(key).all(v));
									} else {
										conditionList.add(Criteria.where(key).all(v));
									}
								} else {
									conditionList.add(Criteria.where(key).all(v));
								}

							}
						} else if (condition.equalsIgnoreCase("in")) {
							Object v = extractValue(type, value);
							if (v instanceof Collection) {
								if (connector != null) {
									if (connector.equalsIgnoreCase("or")) {
										conditionListOr.add(Criteria.where(key).in((Collection) v));
									} else {
										conditionList.add(Criteria.where(key).in((Collection) v));
									}
								} else {
									conditionList.add(Criteria.where(key).in((Collection) v));
								}

							} else {
								if (connector != null) {
									if (connector.equalsIgnoreCase("or")) {
										conditionListOr.add(Criteria.where(key).in(v));
									} else {
										conditionList.add(Criteria.where(key).in(v));
									}
								} else {
									conditionList.add(Criteria.where(key).in(v));
								}

							}
						} else if (condition.equalsIgnoreCase("ne")) {
							if (connector != null) {
								if (connector.equalsIgnoreCase("or")) {
									conditionListOr.add(Criteria.where(key).ne(extractValue(type, value)));
									;
								} else {
									conditionList.add(Criteria.where(key).ne(extractValue(type, value)));
								}
							} else {
								conditionList.add(Criteria.where(key).ne(extractValue(type, value)));
							}

						} else {
							if (connector != null) {
								if (connector.equalsIgnoreCase("or")) {
									conditionListOr.add(Criteria.where(key).is(extractValue(type, value)));
								} else {
									conditionList.add(Criteria.where(key).is(extractValue(type, value)));
								}
							} else {
								conditionList.add(Criteria.where(key).is(extractValue(type, value)));
							}

						}
					}
				}
			}
			if (c != null) {
				conditionList.add(c);
			}
			if (conditionListOr.size() > 0) {
				criteriaOr.orOperator(conditionListOr.toArray(new Criteria[conditionListOr.size()]));
				conditionList.add(criteriaOr);
			}
			if (conditionList.size() > 0) {
				criteria.andOperator(conditionList.toArray(new Criteria[conditionList.size()]));
				query.addCriteria(criteria);
			}

		}
	}

	private Object extractValue(String type, String value) {
		Object result = value;
		if ("boolean".equalsIgnoreCase(type)) {
			result = Boolean.parseBoolean(value);
		} else if ("integer".equalsIgnoreCase(type)) {
			result = Integer.parseInt(value);
		} else if ("long".equalsIgnoreCase(type)) {
			result = Long.parseLong(value);
		} else if ("double".equalsIgnoreCase(type)) {
			result = Double.parseDouble(value);
		} else if ("float".equalsIgnoreCase(type)) {
			result = Float.parseFloat(value);
		} else if ("date".equalsIgnoreCase(type)) {
			try {
				result = DateUtil.parse(value, DateUtil.YEAR_MONTH_DAY_PATTERN);
			} catch (ParseException e) {
				throw new RuntimeException("参数日期格式错误，应为：yyyy-MM-dd", e);
			}
		} else if ("date+1".equalsIgnoreCase(type)) {
			try {
				result = DateUtil.addDate(DateUtil.parse(value, DateUtil.YEAR_MONTH_DAY_PATTERN), 1);
			} catch (ParseException e) {
				throw new RuntimeException("参数日期格式错误，应为：yyyy-MM-dd", e);
			}
		} else if ("datetime".equalsIgnoreCase(type)) {
			try {
				result = DateUtil.parse(value, DateUtil.YMDHMS_PATTERN);
			} catch (ParseException e) {
				throw new RuntimeException("参数日期格式错误，应为：yyyy-MM-dd HH:mm:ss", e);
			}
		} else if ("array-integer".equalsIgnoreCase(type)) {
			List<String> vs = Arrays.asList(value.split(","));
			result = new ArrayList<Integer>();
			for (String v : vs) {
				((List) result).add(new Integer(v));
			}
		} else if ("array".equalsIgnoreCase(type)) {
			result = Arrays.asList(value.split(","));
		} else if ("list".equalsIgnoreCase(type)) {
			value = StringUtils.trimFirstAndLastChar(value, '[');
			value = StringUtils.trimFirstAndLastChar(value, ']');
			List<String> list = new ArrayList<>();
			Object[] arr = value.split(",");
			for (int i = 0; i < arr.length; i++) {
				list.add(arr[i].toString().trim());
			}
			result = list;
		} else if ("null".equalsIgnoreCase(type)) {
			result = null;
		} else if ("empty".equalsIgnoreCase(type)) {
			result = "";
		}

		return result;
	}

	private static List<String> excludeKeyList = new ArrayList<String>() {
		{
			add("nd");
			add("sord");
			add("rows");
			add("sidx");
			add("_search");
			add("page");
			add("size");
			add("sortField");
			add("sortOrder");
			add("_");
		}
	};
}
