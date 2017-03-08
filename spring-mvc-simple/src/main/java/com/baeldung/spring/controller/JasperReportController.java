package com.baeldung.spring.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class JasperReportController {
	@RequestMapping("/jasper")
	@ResponseBody
	public ModelAndView getReport(HttpServletRequest request, HttpServletResponse response) throws JRException, IOException {
	        InputStream jasperStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("jasper/JREmp1.jasper");
	        Map<String, Object> params = new HashMap<>();
	        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);

	        Map parameters = new HashMap();
	        parameters.put("Title", "List of Contacts");
	        parameters.put("noy", 10);

	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());

	        response.setContentType("application/x-pdf");
	        response.setHeader("Content-disposition", "inline; filename=helloWorldReport.pdf");

	        final OutputStream outStream = response.getOutputStream();
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		ModelAndView model = new ModelAndView("Greeting");
		return model;
	}
}