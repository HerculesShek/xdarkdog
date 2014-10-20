package com.xdarkdog.web.util;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class FormUtil {
	// 设置上传文件最大为 3M
	private static final long MAX_SIZE = 30 * 1024 * 1024;
	// 允许上传的文件格式的列表
	private static final String[] ALLOW_TYPE = new String[] { "image/bmp",
			"image/x-png", "image/png", "image/gif", "image/jpeg", "image/jpg", "image/pjpeg" };

	
	public List<FileItem> getFileList(HttpServletRequest request, String filePath) {
		// 设置磁盘工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(20 * 1024 * 1024); // 设置缓冲区
		File repo = new File(request.getServletContext().getRealPath(filePath));
		factory.setRepository(repo); // 设置图片保存位置
		// 文件上传的工具类
		ServletFileUpload handler = new ServletFileUpload(factory);
		handler.setSizeMax(MAX_SIZE);// 设置文件上传的最大尺寸
		handler.setHeaderEncoding("utf-8"); // 设置头文件的编码方式

		List<FileItem> files = null;
		try {
			files = handler.parseRequest(request);
		} catch (FileUploadException e) {
			System.out.println("文件上传异常！");
			e.printStackTrace();
		}
		return files;
	}

	/**
	 * 
	 * @param request
	 * @param clazz 要封装成哪种类型，传入类
	 * @param filePath 图片的存放地址
	 * @return Object 该方法可以完成文件上传操作（包括多文件上传），能接收表单中所有大部分控件注册信息 ，并封装成Bean
	 *         返回实例对象，完成填充 POJO 层的操作。
	 */
	// TODO 把这个方法封装为增加和更新同时能够处理的
	public Object getInstanceByAdvanceForm(HttpServletRequest request, 
										   List<FileItem> files,
			                               Class<?> clazz, 
			                               String filePath) {
		Object object = null;
		
		// MultiMap是一种 Map之后的特殊结构，允许一个主键存放多个值，弥补了原生Map的不足
		// 如果表单控件中有一些同名的控件值都需要用一个key来存放时，是再合适不过的选择
		MultiMap components = new MultiValueMap();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		try {
			Iterator<FileItem> it = files.iterator();
			while (it.hasNext()) {
				FileItem item = it.next();
				if (item.isFormField()) { // 是一个普通的表单域
					// item.getFieldName() 指的是表单项的名字 比如 fruit_shop_name
					// item.getString("utf-8") 则是value值
					components.put(item.getFieldName(), item.getString("utf-8"));
					System.out.println("FieldName:"+item.getFieldName()+", value:"+item.getString("utf-8"));
				} else { // TODO　应该首先判断是不是有文件上传
					// 可以在此处 标记断点，来测试一下上传文件的类型
					// item.getName() 在这里则是上传的文件的名字，没有任何的路径信息
					String filename = item.getName();
					
					if (filename !=null && !filename.equals("") && ArrayUtils.contains(ALLOW_TYPE, item.getContentType())) {
						System.out.println("上传的文件的名字是->"+filename);
						// 对文件的名字做一些处理
						if (filename.length() > 40) {
							filename = filename.substring(0, 40);
						}
						String now = String.valueOf(System.currentTimeMillis());
						filename = now + filename;
						File file2server = new File(request.getServletContext().getRealPath(filePath), filename);
						item.write(file2server);
						String file2db = request.getContextPath()
								+ "/"
								+ filePath
								+ "/"
								+ filename;
						components.put(item.getFieldName(), file2db);
						System.out.println("存入数据库的文件路径是："+file2db);
					} 
				}
			}
			paramsMap = convertParmas(components);
			object = clazz.newInstance();   
			BeanUtils.populate(object, paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

	//表单中没有二进制文件上传的时候，可以应用此方法。
	public Object getInstanceBySimpleForm( HttpServletRequest request , Class<?> clazz ) {
		Object object = null;
		try {
			object = clazz.newInstance();
			Map<String,String[]> parameterMap = request.getParameterMap();
			Map<String , Object > params = new HashMap<String,Object>();
			for (Iterator<String> iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				String[] values = parameterMap.get(key);
				if (values.length >= 1) {
					String context = StringUtils.join(values, ",");
					params.put(key, context);
					continue;
				}
				params.put(key, values[0]);
			}
			BeanUtils.populate( object , params );
			return object;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	// 该方法适用于：比如一个产品需要修改产品图片，但是其他信息没必要修改时，则只重新上传文件，并返回新的路径即可。
	@SuppressWarnings("unchecked")
	public String UpdateFile(HttpServletRequest request, String filePath) {
		// 设置磁盘工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(20 * 1024 * 1024);
		factory.setRepository(new File(request.getServletContext().getRealPath(filePath))); // 设置图片保存位置

		// 文件上传的工具类
		ServletFileUpload handler = new ServletFileUpload(factory);
		handler.setSizeMax(MAX_SIZE);// 设置文件上传的最大尺寸
		handler.setHeaderEncoding("utf-8"); // 设置头文件的编码方式。

		try {
			//保存图片的路径
			StringBuilder url = new StringBuilder("");
			//将表单中所有控件都存放到 List 容器中 , 并让每个控件的类型都为 FileItem
			List<FileItem> files = handler.parseRequest(request);
			Iterator<FileItem> it = files.iterator();

			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					if (ArrayUtils.contains(ALLOW_TYPE, item.getContentType())) { // 如果上传文件类型，在允许范围之内，再做处理。
						// 获取文件名.
						String filename = item.getName();
						// 声明服务器端的文件，等待文件上传，填充。
						File filetoserver = new File(request.getServletContext().getRealPath(filePath), filename);
						// 实现文件上传
						item.write(filetoserver);
						// 声明相对路径， 此路径要保存到Bean中 ， 例如
						// product.setImg("/WebDemo2/upload/xxx.jpg" );
						String filetodb = request.getContextPath()
								+ File.separator
								+ filePath
								+ File.separator
								+ filename;
						url.append(filetodb).append(",");
					} else {
						return null;
					}
				}
			}
			System.out.println(url.toString());
			return url.deleteCharAt(url.lastIndexOf(",")).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//将 MultiMap 转化成 原生的 Map
	@SuppressWarnings("rawtypes")
	private Map<String, Object> convertParmas(MultiMap components) {
		Map<String, Object> params = new HashMap<String, Object>();
		Set keySet = components.keySet();
		Iterator keyIterator = keySet.iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			Collection<Object> values = (Collection<Object>) components.get(key);
			Iterator<Object> valuesIterator = values.iterator();
			if (values.size() <= 1) {
				params.put((String) key, valuesIterator.next());
			} else {
				StringBuilder builder = new StringBuilder();
				while (valuesIterator.hasNext()) {
					builder.append(valuesIterator.next()).append(",");
				}
				String builder_str = builder.deleteCharAt(builder.lastIndexOf(",")).toString();
				params.put((String) key, builder_str);
			}
		}
		return params;
	}
}
