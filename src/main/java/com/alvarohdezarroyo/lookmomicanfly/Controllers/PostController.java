package com.alvarohdezarroyo.lookmomicanfly.Controllers;

import com.alvarohdezarroyo.lookmomicanfly.Services.SellingFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private final SellingFeeService sellingFeeService;

    public PostController(SellingFeeService sellingFeeService) {
        this.sellingFeeService = sellingFeeService;
    }

}
