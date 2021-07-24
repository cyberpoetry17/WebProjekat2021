package web2021;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.put;
import static spark.Spark.staticFiles;

import java.io.File;
import java.security.Key;

import com.google.gson.Gson;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import spark.Request;
import spark.Response;
import spark.Route;

import web2021.controller.*;
import web2021.service.TestService;

public class Application
{

	public static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	public static Gson gson;

	public static File uploadDir;
	
	public static TestService testService;

	public static String parseJws(Request request)
	{
		String auth = request.headers("Authorization");
		if (auth == null || auth.isEmpty() || !auth.contains("Bearer"))
		{
			return null;
		}
		
		String jws = auth.substring(auth.indexOf("Bearer") + 7);
		if(jws.equalsIgnoreCase("undefined") || auth.length() <= 7)
		{
			return null;
		}

		return jws;
	}
	
	
	public static Route serveStaticResource = (Request request, Response response) ->
	{
		response.redirect("/static/index.html");
		return response;
	};
	
	private static void enableCORS(final String origin, final String methods, final String headers) {

	    options("/*", (request, response) -> {

	        String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
	        if (accessControlRequestHeaders != null) {
	            response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
	        }

	        String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
	        if (accessControlRequestMethod != null) {
	            response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
	        }

	        return "OK";
	    });

	    before((request, response) -> {
	        response.header("Access-Control-Allow-Origin", origin);
	        response.header("Access-Control-Request-Method", methods);
	        response.header("Access-Control-Allow-Headers", headers);
	        // Note: this may or may not be necessary in your particular application
	        response.type("application/json");
	    });
	}

	public static void main(String args[])
	{
		gson = new Gson();
		
		testService = new TestService("tests.json");
		
		uploadDir = new File("upload");
		uploadDir.mkdir();

		port(8080);
//		uncomment if creating jar
//		staticFiles.location("/static");
//		comment if creating jar
		String projectDir = System.getProperty("user.dir");
		String staticDir = "/src/main/resources/static";
		staticFiles.externalLocation(projectDir + staticDir);
		
		enableCORS("*", "*", "*");
		
		get("/", serveStaticResource);
		
		post("rest/test/add-test", TestController.addTest);
		
		put("rest/test/update-test", TestController.updateTest);
		
		get("rest/test/get-all", TestController.getAll);
		
		delete("rest/test/delete-test/:id", TestController.deleteTest);
		
		get("*", (request, response) ->
		{
			System.out.println("Redirecting...");
			response.redirect("/");
			return response;
		});
		
	}

}
