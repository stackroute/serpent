package com.stackroute.serpent.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/* The FbController is created by injecting a Facebook object into its constructor.
 The Facebook object is a reference to Spring Social's Facebook API binding.*/
@Controller
public class FbController {
    @Autowired
    Facebook facebook;
    @Autowired
    ConnectionRepository connectionRepository;

    /*The `getFeeds()` method is annotated with `@RequestMapping` to indicate that it should handle
     GET requests for the root path (/).

     The first thing the method does is check whether the user has authorized the application to
     access the user's Facebook data. If not, the user is redirected to `ConnectController` with the
     option to kick off the authorization process.

    If the user has authorized the application to access Facebook data,
    the application fetches the the user's feed. The data is placed into the model to be displayed by the
    view identified as "feed".
    */
    @GetMapping("/")
    public String getFeeds(Model model) {
        if(connectionRepository.findPrimaryConnection(Facebook.class)==null){
            return "redirect:/connect/facebook";
        }
       PagedList<Post> feed=facebook.feedOperations().getFeed();
       System.out.println(feed);
       model.addAttribute("feed",feed);
        return "feed";
    }
}
