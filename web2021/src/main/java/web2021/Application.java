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
import java.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import spark.Request;
import spark.Response;
import spark.Route;

import web2021.controller.*;
import web2021.service.AdministratorService;
import web2021.service.ArticleService;
import web2021.service.CourierService;
import web2021.service.CustomerService;
import web2021.service.CustomerTypeService;
import web2021.service.FileService;
import web2021.service.ManagerService;
import web2021.service.OrderService;
import web2021.service.RestaurantService;
import web2021.service.TestService;
import web2021.service.UserService;
import web2021.utils.LocalDateTimeDeserializer;
import web2021.utils.LocalDateTimeSerializer;

public class Application
{

	public static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

	private static GsonBuilder gsonBuilder = new GsonBuilder();
	
	public static Gson gson;

	public static File uploadDir;
	
	public static TestService testService;
	public static UserService userService;
	public static CustomerService customerService;
	public static CustomerTypeService customerTypeService;
	public static ManagerService managerService;
	public static AdministratorService administratorService;
	public static CourierService courierService;
	public static RestaurantService restaurantService;
	public static FileService fileService;
	public static ArticleService articleService;
	public static OrderService orderService;
	
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
	
	public static Gson createCustomGson() {
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
		Gson gson = gsonBuilder.create();
		return gson;
	}
	
	public static void main(String args[])
	{
		gson = createCustomGson();
		
		testService = new TestService("tests.json");
		userService = new UserService();
		customerService = new CustomerService("customers.json");
		customerTypeService  = new CustomerTypeService("customertypes.json");
		managerService = new ManagerService("managers.json");
		administratorService = new AdministratorService("administrators.json");
		courierService = new CourierService("couriers.json");
		restaurantService = new RestaurantService("restaurants.json");
		fileService = new FileService();
		articleService = new ArticleService();
		orderService = new OrderService();

		
		uploadDir = new File("src/main/resources/static/upload");
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
		post("rest/customer/register", CustomerController.register);
		post("rest/user/login", UserController.login);
		post("rest/courier/register", CourierController.register);
		post("rest/manager/register", ManagerController.register);
		post("rest/file/upload", FileController.upload);
		post("rest/restaurant/create-restaurant", RestaurantController.createRestaurant);
		post("rest/article/add-article", ArticleController.addArticle);
		post("rest/article/check-is-name-taken", ArticleController.checkIsNameTaken);
		post("rest/article/update-article", ArticleController.updateArticle);
		post("rest/customer/add-article", CustomerController.addArticle);
		
		put("rest/test/update-test", TestController.updateTest);
		put("rest/user/edit-profile", UserController.editProfile);
		put("rest/order/cancel-order",OrderController.cancelOrder);
		
		get("rest/test/get-all", TestController.getAll);
		get("rest/user/check-username/:username", UserController.checkUsername);
		get("rest/customer/get-all-customers", CustomerController.getAllCustomers);
		get("rest/courier/get-all-couriers", CourierController.getAllCouriers);
		get("rest/manager/get-all-managers", ManagerController.getAllManagers);
		get("rest/manager/get-managers-without-restaurant", ManagerController.getManagersWithoutRestaurant);
		get("rest/restaurant/get-all-restaurant-types", RestaurantController.getAllRestaurantTypes);
		get("rest/restaurant/get-all-restaurants", RestaurantController.getAllRestaurants);
		get("rest/article/get-all-aricle-types", ArticleController.getAllArticleTypes);
		get("rest/article/get-all-articles/:id", ArticleController.getAllArticles);
		get("rest/article/get-available-articles/:id", ArticleController.getAvailableArticles);
		get("rest/order/make-order/:id", OrderController.makeOrder);
		get("rest/order/get-order-status", OrderController.getOrderStatus);
		get("rest/order/get-orders-for-customer/:id", OrderController.getOrdersForCustomer);
		get("rest/order/get-orders-for-courier/:id", OrderController.getOrdersForCourier);
		get("rest/order/get-orders-for-manager/:id", OrderController.getOrdersForManager);

		
		delete("rest/test/delete-test/:id", TestController.deleteTest);
		
		get("*", (request, response) ->
		{
			System.out.println("Redirecting...");
			response.redirect("/");
			return response;
		});
		
	}

}
