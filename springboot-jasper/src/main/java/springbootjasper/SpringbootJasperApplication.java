package springbootjasper;

import net.sf.jasperreports.engine.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class SpringbootJasperApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJasperApplication.class, args);
	}

	@Bean
	CommandLineRunner init(){
		return args -> {
			String destinationPath = "src" +
					File.separator + "main" +
					File.separator + "resources" +
					File.separator + "static" +
					File.separator + "ReportGenerated.pdf";

			String filePath = "src" +
					File.separator + "main" +
					File.separator + "resources" +
					File.separator + "templates" +
					File.separator + "report" +
					File.separator + "report.jrxml";

			LocalDateTime localDateTime = LocalDateTime.now();
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			Map<String,Object> parameters= new HashMap<>();
			parameters.put("voucher_id","0000003212");
			parameters.put("date_report",timeFormatter.format(localDateTime));
			parameters.put("value_pay", new BigDecimal(300000));
			parameters.put("payment_method","Cash");
			parameters.put("payer","Carlos Manrique");
			parameters.put("debtor","Jorge Barrigal");
			parameters.put("imageDir","classpath:/static/images/");

			JasperReport report = JasperCompileManager.compileReport(filePath);
			JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource() );
			JasperExportManager.exportReportToPdfFile(print,destinationPath);

			System.out.println("Report Created successfully");
		};
	}

}
