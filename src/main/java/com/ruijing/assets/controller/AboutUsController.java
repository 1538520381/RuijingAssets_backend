package com.ruijing.assets.controller;


import com.ruijing.assets.dao.AboutUsDao;
import com.ruijing.assets.entity.dto.AboutUsDto;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.entity.vo.AboutUsVo;
import com.ruijing.assets.service.AboutUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assets/about_us")
public class AboutUsController {

    @Autowired
    AboutUsService aboutUsService;

    @GetMapping("/list")
    /*
     * @author: K0n9D1KuA
     * @description: 联系我们信息查询
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 15:37
     */

    public R list() {
        AboutUsVo aboutUsVo = aboutUsService.getList();
        return R.success(aboutUsVo);
    }


    /*
     * @author: K0n9D1KuA
     * @description: 修改联系我们信息
     * @param: aboutUsDto 要修改的实体类
     * @return: com.ruijing.assets.entity.result.R
     * @date: 2023/2/7 15:43
     */
    @PostMapping("/update")
    public R update(@RequestBody AboutUsDto aboutUsDto) {
        aboutUsService.updateInfo(aboutUsDto);
        return R.ok();
    }
}
