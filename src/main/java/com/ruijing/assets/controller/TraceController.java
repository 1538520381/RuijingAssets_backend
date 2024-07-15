package com.ruijing.assets.controller;

import com.ruijing.assets.entity.pojo.TraceEntity;
import com.ruijing.assets.entity.result.R;
import com.ruijing.assets.service.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Persolute
 * @version 1.0
 * @description
 * @email 1538520381@qq.com
 * @date 2024/06/14 18:53
 */
@RestController
@RequestMapping("assets/trace")
public class TraceController {
    @Autowired
    private TraceService traceService;

    @PostMapping
    private R post(@RequestBody TraceEntity trace) {
        traceService.save(trace);
        return R.ok();
    }

    @PutMapping
    private R update(@RequestBody TraceEntity trace) {
        traceService.updateById(trace);
        return R.ok();
    }

    @GetMapping("/{userId}")
    private R get(@PathVariable Long userId) {
        return R.ok().put("data", traceService.getAllByUser(userId));
    }

    @GetMapping("/{investorId}/{assetId}/{userId}")
    private R get(@PathVariable Long investorId, @PathVariable Long assetId, @PathVariable Long userId) {
        return R.ok().put("data", traceService.get(investorId, assetId, userId));
    }

    @GetMapping("/unend/{userId}")
    private R getUnend(@PathVariable Long userId) {
        return R.ok().put("data", traceService.getUnend(userId));
    }
}
