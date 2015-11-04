package com.spilna.sprava.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Post;
import com.spilna.sprava.model.PostRO;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.restfb.DefaultFacebookClient;
import com.restfb.Facebook;
import com.restfb.types.User;
import com.spilna.sprava.model.PostInf;
import com.spilna.sprava.service.PostService;
import com.spilna.sprava.service.UserService;

/**
 * 
 * @author Ivanov 
 * @version 1.0
 */

@Controller
public class FacebookController {

	/**
	 * Link to service users and post.
	 */
	@Autowired
	private UserService userService;
	@Autowired
	private PostService messageService;

	/**
	 * The global variable that contains the user Access Token.
	 */

	private String accessToken;

	/**
	 * The global variable that contains a listing of the data to be provided by
	 * the application.
	 */
	private static final String SCOPE = "publish_actions,user_about_me,user_birthday,user_posts,manage_pages";

	/**
	 * The global variable that contains the URL address redirection after
	 * successful login to Facebook.
	 */
	private static final String REDIRECT_URI = "http://localhost:8080/callback";

	/**
	 * The global variable that contains the application ID.
	 */
	private static final String APP_ID = "913574692064006";

	/**
	 * The global variable that contains the application Secret.
	 */
	private static final String APP_SECRET = "deffcd8a2f3c657b5e11fa5cc956a15e";

	/**
	 * The global variable that contains the URL address of dialogue
	 * authorization on Facebook via OAuth.
	 */
	private static final String DIALOG_OAUTH = "https://www.facebook.com/dialog/oauth";

	/**
	 * The global variable that contains the URL address to obtain the Access
	 * Token.
	 */
	private static final String ACCESS_TOKEN = "https://graph.facebook.com/oauth/access_token";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView homePage() {
		return new ModelAndView("index");// name of the jsp-file in the 'pages'
											// folder

	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView indexPage() {
		return new ModelAndView("index");
	}

	/**
	 * The method performs a redirect to the login page on Facebook.
	 * 
	 * @param request
	 *            the resulting value of HttpServletRequest
	 * @param response
	 *            corresponding to the value of HttpServletResponse
	 * @throws IOException
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public void getSignin(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");
		response.sendRedirect(DIALOG_OAUTH + "?client_id=" + APP_ID
				+ "&redirect_uri=" + REDIRECT_URI + "&scope=" + SCOPE);
	}

	/**
	 * The method that gets Access Token.
	 * 
	 * @param request
	 *            the resulting value of HttpServletRequest
	 * @param response
	 *            corresponding to the value of HttpServletResponse
	 * @throws IOException
	 */
	@RequestMapping(value = "/callback", method = RequestMethod.GET)
	public void accessCode(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String code = request.getParameter("code");
		if (code == null || code.equals("")) {
			// error
		}
		String urlAccessToken = ACCESS_TOKEN + "?client_id=" + APP_ID
				+ "&redirect_uri=" + REDIRECT_URI + "&client_secret="
				+ APP_SECRET + "&code=" + code;
		response.setContentType("text/html");
		URL url = new URL(urlAccessToken);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setConnectTimeout(10000);
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(urlConnection.getInputStream()));
		String inputLine;
		StringBuilder stringBuilder = new StringBuilder();
		while ((inputLine = bufferedReader.readLine()) != null) {
			stringBuilder.append(inputLine + "\n");
		}

		bufferedReader.close();
		this.setAccessToken(stringBuilder.toString());
		/*
		 * Get access token
		 */
		accessToken = this.getAccesToken();
		this.setAccessToken(accessToken.substring(13, accessToken.indexOf('&')));
		System.out.println("Save data user in data base--->");
		userService.addUser(this.getAccesToken());
		response.sendRedirect("http://localhost:8080/post");

	}

	/**
	 * The method returns a JSP page "post" when the link "/post".
	 * The user can post it status or see  of their posts.
	 * @param post
	 * @return
	 */

	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public ModelAndView getMessageForm(@ModelAttribute("post") PostInf post) {

		ModelAndView modelAndView = new ModelAndView("post");
	/**
	 * 
	 * DefaultFacebookClient is the FacebookClient implementation
	 *  that ships with RestFB. You can customize it by passing in
	 * custom JsonMapper and WebRequestor implementations, or simply
	 * write your own FacebookClient instead for maximum control.
	 */
        FacebookClient facebookClient = new DefaultFacebookClient(this.getAccesToken(), APP_SECRET);
		User me = facebookClient.fetchObject("me", User.class);
		/*
		 * variable contains user Id 
		 */
		
		String idU = me.getId();
		/*
		 * FQL query. Select message and status ID with facebook DB table"status"
		 */
		String query = "SELECT  message,status_id FROM status WHERE uid=?";


		List<Post> posts = facebookClient.fetchConnection("me/feed", Post.class, Parameter.with("fields",
                "description,message")).getData();
		List<PostRO> postlist = new ArrayList<PostRO>();

		/*
		 *  create an array of posts using the method split()
		 */
		//String[] postsU = posts.toString().split(",");
		Integer count = posts.size() + 1 ; //number of posts
		/*
		 * Get the number of records from  our DB
		 */
		int countMy=messageService.getCountPost(idU);
		/*
		 * Fill database table if it is empty
		 */
		System.out.println("Number FB post=" + count
							+ "\n Number records our DB=" + countMy);
		
		if (messageService.getMessage(idU).isEmpty()){
            for (Post post1 : posts) {
                messageService.saveMessage(post1.getId(), idU,
                        post1.getMessage() == null ? post1.getDescription() : post1.getMessage());
            }
			
//			for (int i = count; i <= count & i >= 0; i--) {
//
//				System.out.println("Fill the empty table DB:");
//				// Save posts
//			 	messageService.saveMessage(postsU[i].substring(1, 16), idU,
//										   postsU[i].substring(17));
//
//			}
		}
		
		/*
		 * Fill database table if facebook has a new record
		 */

//		else if(count > countMy){
//
//				int diff=(count-countMy) - 1;
//
//				do{
//					System.out.println("add post::"+posts.get(diff));
//				messageService.saveMessage(posts.get(diff).getId(), idU,posts.get(diff).getMessage());
//				diff--;
//				}while(diff>=0);
//			}

		postlist=messageService.getMessage(idU);
		modelAndView.addObject("post", postlist);
		modelAndView.addObject("user", userService.getUser(idU));
		
		
		return modelAndView;
			
	}

	/**
	 * Definition of user input. Saving the new post to the database
	 *  and publishing the post on facebook
	 * @param post
	 * @return ModelAndView redirect:/social/facebook/post.html
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@RequestMapping(value="/savePost", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView saveUserMessage(@ModelAttribute("post") PostInf post,BindingResult result)
			throws ClientProtocolException, IOException {

		messageService.addMessage(this.getAccesToken(), post);

		System.out.println("Save User Message");
		return new ModelAndView("redirect:/post.html");// redirected

	}

    @RequestMapping(value = "/ukrainMap", method = RequestMethod.GET)
    public ModelAndView lookMap()
            throws IOException {
        ModelAndView modelAndView = new ModelAndView("ukrainMap");
        Map<String, String> map = new HashMap<>();
        map.put("kuyiv", "80");
        modelAndView.addObject("values", map);

        return modelAndView;

    }

    public void setAccessToken(String token) {
		this.accessToken = token;
	}

	public String getAccesToken() {
		return this.accessToken;
	}
}
