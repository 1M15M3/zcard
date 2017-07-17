package com.ftsafe.iccd.huipan.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.axis.common.JacksonUtil;
import com.axis.common.Log;
import com.axis.common.Stringutil;
import com.axis.common.log.LogWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ftsafe.iccd.SpringContextHolder;
import com.ftsafe.iccd.ZcardConfig;
import com.ftsafe.iccd.core.dao.impl.HuiPanDAOImpl;
import com.ftsafe.iccd.core.dao.impl.HuiPanTablesManagerDAOImpl;
import com.ftsafe.iccd.core.model.HuiPan;
import com.ftsafe.iccd.core.model.HuiPanTablesManager;
import com.ftsafe.iccd.huipan.CODE;
import com.ftsafe.iccd.huipan.MSG;
import com.ftsafe.iccd.huipan.log.HuiPanDataJson;
import com.ftsafe.iccd.personalize.ftpmsapi.API;
import com.ftsafe.iccd.personalize.ftpmsapi.FTPMSCODE;
import com.ftsafe.iccd.personalize.ftpmsapi.mapper.SignInMapper;
import com.ftsafe.iccd.personalize.http.Http;
import com.ftsafe.iccd.personalize.http.Http.HttpCallback;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final static LogWrapper LOG = Log.get();

	public static Map<String, Object> TokenMap = new HashMap<String, Object>();

	private Map<String, Object> map = null;

	private String tokenKey = null;

	// private JsonGenerator jsonGenerator = new ObjectMapper()
	// private static JsonBinder binder = JsonBinder.buildNonDefaultBinder();

	// ModelAttribute 注解 每次请求都会执行该方法
	@ModelAttribute
	private void setResponse(HttpServletResponse response) {
		// 设置Ajax跨域访问
		response.setHeader("Access-Control-Allow-Origin", "*");
	}

	// 授权
	// @ResponseBody
	// public Map<String, Object> authorization() {
	// return null;
	// }
	@RequestMapping(value = "/a", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ModelAndView authorization(Model model,
			final HttpServletRequest request) {
		// sign in account
		String u = request.getParameter("u");
		String p = request.getParameter("p");
		// 用户登录名就是token Key
		tokenKey = u;
		// 初始化HashMap
		if (map == null)
			map = new HashMap<String, Object>();
		// 验证用户
		// 请求登录服务
		Http.Get(ZcardConfig.FTPMS_HOST + API.SIGNIN, new HttpCallback() {

			@Override
			public void solve(String result) {
				try {
					SignInMapper sign = JacksonUtil.toBean(result,
							SignInMapper.class);
					// Code 和 Message 信息存入响应 Map
					map.put("code", sign.getCode());
					map.put("msg", sign.getMsg());
					if (FTPMSCODE._000000.equals(sign.getCode())) {
						// 生成 Token
						String token = request.getSession().getId();
						// 保存 Token
						if (!Stringutil.isEmpty(tokenKey))
							putToken(tokenKey, token);
						// Token 保存到响应 Map
						map.put("token", token);

						LOG.debug("{} 申请了 Zcard 的授权, 申请的 token {}", tokenKey,
								token);
					} else {
						map.put("token", null);
					}

				} catch (JsonParseException e) {
					LOG.warn(e.getMessage(), e);
				} catch (JsonMappingException e) {
					LOG.warn(e.getMessage(), e);
				} catch (IOException e) {
					LOG.warn(e.getMessage(), e);
				}

			}

			@Override
			public void fail() {
				map.put("code", CODE._9999);
				map.put("msg", MSG._9999);
				map.put("token", null);
			}
		}, "operator", u, "pwd", p);

		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	@RequestMapping(value = "/r", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ModelAndView revocation(Model model, HttpServletRequest request) {
		// sign in account
		String u = request.getParameter("u");
		String p = request.getParameter("p");
		String token = request.getParameter("token");

		// 实例化 map
		if (map == null)
			map = new HashMap<String, Object>();
		// token 验证不通过
		if (!isAuth(token)) {
			map.put("code", CODE._1000);
			map.put("msg", MSG._1000);
			map.put("token", token);
		}
		// token 验证通过
		else {
			// 用户登录名就是token Key
			tokenKey = u;
			// 验证用户
			// 请求登录服务
			Http.Get(ZcardConfig.FTPMS_HOST + API.SIGNIN, new HttpCallback() {

				@Override
				public void solve(String result) {
					try {
						SignInMapper sign = JacksonUtil.toBean(result,
								SignInMapper.class);
						// 删除 Token
						removeToken(tokenKey);

						map.put("code", sign.getCode());
						map.put("msg", sign.getMsg());
						// 检查是否删除
						String token = (String) TokenMap.get(tokenKey);
						map.put("token", token);

						LOG.debug("{} 撤销了 Zcard 的授权, 撤销的 token {}", tokenKey,
								token);
					} catch (JsonParseException e) {
						LOG.warn(e.getMessage(), e);
					} catch (JsonMappingException e) {
						LOG.warn(e.getMessage(), e);
					} catch (IOException e) {
						LOG.warn(e.getMessage(), e);
					}

				}

				@Override
				public void fail() {
					map.put("code", CODE._9999);
					map.put("msg", MSG._9999);
					map.put("token", null);
				}
			}, "operator", u, "pwd", p);
		}
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	@RequestMapping(value = "/huipantbl", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ModelAndView viewHuiPanTable(HttpServletRequest request) {
		try {
			int code = 0;
			String msg = null;
			List<HuiPanTablesManager> list = null;
			// 获取参数token
			String token = request.getParameter("token");

			if (!isAuth(token)) {
				msg = MSG._1000;
				code = CODE._1000;
			} else {
				HuiPanTablesManagerDAOImpl hpmd = SpringContextHolder
						.getContext().getBean(HuiPanTablesManagerDAOImpl.class);
				list = hpmd.listTables();
				if (list == null || list.isEmpty()) {
					code = CODE._1002;
					msg = MSG._1002;
				} else {
					code = CODE._9000;
					msg = MSG._9000;

				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", code);
			map.put("msg", msg);
			map.put("tblList", list);
			System.out.println(list.get(0).getCreateTime());
			return new ModelAndView(new MappingJackson2JsonView(), map);
		} catch (Exception e) {
			LOG.warn(e.getMessage(), e);
		}
		return null;
	}

	@RequestMapping(value = "/project/c", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ModelAndView createProject(HttpServletRequest request) {
		int code;
		String msg = null;
		// 获取参数token
		String token = request.getParameter("token");
		// 获取项目名称
		String projectName = request.getParameter("projectName");
		// 获取表名称
		String tableName = request.getParameter("tableName");
		// 状态
		int status = Integer.valueOf(request.getParameter("status"));

		if (!isAuth(token)) {
			msg = MSG._1000;
			code = CODE._1000;
		} else {
			HuiPanTablesManagerDAOImpl hpmd = SpringContextHolder.getContext()
					.getBean(HuiPanTablesManagerDAOImpl.class);
			int ret = hpmd.create(tableName, projectName, status);
			if (ret == 0) {
				ret = hpmd.createTable(tableName);
				if (ret == 0) {
					msg = MSG._9000;
					code = CODE._9000;
				} else {
					msg = MSG._1001 + tableName;
					code = CODE._1001;
				}
			} else {
				msg = MSG._1002;
				code = CODE._1002;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		LOG.debug("code {} msg {}", code, msg);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	@RequestMapping(value = "/huipandata", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ModelAndView getHuiPan(HttpServletRequest request) {
		int code = 0;
		String msg = null;
		String url = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// token
			String token = request.getParameter("token");
			// project name
			String projectName = request.getParameter("projectName");
			// verify token
			if (!isAuth(token)) {
				msg = MSG._1000;
				code = CODE._1000;
			} else {
				// 获取数据
				// table name
				HuiPanTablesManagerDAOImpl hpmdi = SpringContextHolder
						.getContext().getBean(HuiPanTablesManagerDAOImpl.class);
				String tableName = hpmdi.getTableBy(projectName).getTableName();
				if (Stringutil.isEmpty(tableName)) {
					code = CODE._1005;
					msg = projectName + MSG._1005 + tableName;
				} else {
					HuiPanDAOImpl hpdi = SpringContextHolder.getContext()
							.getBean(HuiPanDAOImpl.class);
					// 根据时间查询回盘数据
					List<HuiPan> list = hpdi.listHuiPans(tableName);
					if (list == null || list.isEmpty()) {
						code = CODE._1002;
						msg = MSG._1002;
					} else {
						// create file name
						String fileName = projectName.concat(".json");
						// 文件路径
						HuiPanDataJson hpdj = new HuiPanDataJson();
						String path = hpdj.mkDirs(fileName);
						// 写数据
						int ret = hpdj.write(path, JacksonUtil.toJson(list));
						// 写数据失败
						if (ret == 0) {
							code = CODE._1006;
							msg = MSG._1006;
						} else {

							code = CODE._9000;
							msg = MSG._9000;
							url = hpdj.downloadUrl;
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
		map.put("code", code);
		map.put("msg", msg);
		map.put("url", url);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	@RequestMapping(value = "/huipandata/r", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ModelAndView viewHuiPan(HttpServletRequest request) {
		int code = 0;
		String msg = null;
		List<HuiPan> huipanList = null;
		try {
			// token
			String token = request.getParameter("token");
			// project name
			String projectName = request.getParameter("projectName");
			// from
			String from = request.getParameter("fromTime");
			// to
			String to = request.getParameter("toTime");
			// verify token
			if (!isAuth(token)) {
				msg = MSG._1000;
				code = CODE._1000;
			} else {
				// table name
				HuiPanTablesManagerDAOImpl hpmdi = SpringContextHolder
						.getContext().getBean(HuiPanTablesManagerDAOImpl.class);
				String tableName = hpmdi.getTableBy(projectName).getTableName();
				// instance HuiPanDao
				HuiPanDAOImpl hpdi = SpringContextHolder.getContext().getBean(
						HuiPanDAOImpl.class);
				if (Stringutil.isEmpty(tableName)) {
					code = CODE._1005;
					msg = projectName + MSG._1005 + tableName;
				} else if (Stringutil.isEmpty(from) && Stringutil.isEmpty(to)) {
					// 查询所有的数据
					huipanList = hpdi.listHuiPans(tableName);
				} else if (!Stringutil.isEmpty(from) && !Stringutil.isEmpty(to)) {
					Timestamp fromTime = new Timestamp(Long.valueOf(from));
					Timestamp toTime = new Timestamp(Long.valueOf(to));
					// 根据时间查询回盘数据
					huipanList = hpdi.listHuiPans2(tableName, fromTime, toTime);
				}
			} // end authorization

			if (huipanList == null || huipanList.isEmpty()) {
				code = CODE._1002;
				msg = MSG._1002;
			} else {
				code = CODE._9000;
				msg = MSG._9000;
			}

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("code", code);
			map.put("msg", msg);
			map.put("huipanDataList", huipanList);
			return new ModelAndView(new MappingJackson2JsonView(), map);
		} catch (Exception e) {
			LOG.warn(e.getMessage(), e);
			return null;
		}
	}

	@RequestMapping(value = "/cardstatus/u", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public ModelAndView modifyStatus(HttpServletRequest request) {
		int code;
		String msg = null;
		// 获取token
		String token = request.getParameter("token");
		// 表名
		String tableName = request.getParameter("tableName");
		// id
		Integer id = Integer.valueOf(request.getParameter("id"));
		// status
		Integer status = Integer.valueOf(request.getParameter("status"));
		// 验证Token
		if (!isAuth(token)) {
			msg = MSG._1000;
			code = CODE._1000;
		} else {
			HuiPanDAOImpl hpdi = SpringContextHolder.getContext().getBean(
					HuiPanDAOImpl.class);
			int ret = hpdi.updateStatus(tableName, id, status);
			if (ret == 0) {
				msg = MSG._9000;
				code = CODE._9000;
			} else {
				msg = MSG._1003;
				code = CODE._1003;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("msg", msg);
		return new ModelAndView(new MappingJackson2JsonView(), map);
	}

	private boolean isAuth(String token) {
		return TokenMap.containsValue(token);
	}

	private void putToken(String key, String token) {
		TokenMap.put(key, token);
	}

	private void removeToken(String key) {
		TokenMap.remove(key);
	}

}
