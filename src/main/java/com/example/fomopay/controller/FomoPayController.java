package com.example.fomopay.controller;

import com.example.fomopay.service.FomoPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fomopay")
public class FomoPayController {

    @Autowired
    private FomoPayService fomoPayService;

    /**
     * 处理销售交易
     *
     * @param stan        系统跟踪号
     * @param amount      交易金额
     * @param description 交易描述
     * @return 交易处理结果
     */
    @GetMapping("/transactions/sale")
    public String processSale(@RequestParam int stan,
                              @RequestParam long amount,
                              @RequestParam String description) {
        try {
            return fomoPayService.sale(stan, amount, description);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    /**
     * 查询交易状态
     *
     * @param stan 系统跟踪号
     * @return 交易状态信息
     */
    @GetMapping("/transactions/{stan}")
    public String getTransactionStatus(@PathVariable int stan) {
        try {
            return fomoPayService.query(stan);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    /**
     * 处理退款请求
     *
     * @param stan 系统跟踪号
     * @return 退款处理结果
     */
    @PutMapping("/transactions")
    public String processRefund(@PathVariable int stan,
                                @PathVariable int amount,
                                @PathVariable String retrievalRef,
                                @PathVariable String description
    ) {
        try {

            // 调用服务层处理退款
            return fomoPayService.refund(
                    stan,
                    amount,
                    retrievalRef,
                    description
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    /**
     * 执行批量结算
     *
     * @return 结算结果
     */
    @GetMapping("/transactions/batch-settlement")
    public String processBatchSettlement() {
        try {
            return fomoPayService.batchSubmit();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    /**
     * 处理交易撤销请求
     *
     * @param stan 系统跟踪号
     * @return 撤销处理结果
     */
    @DeleteMapping("/transactions/{stan}")
    public String processReversal(@PathVariable int stan) {
        try {
            // 格式化requestBody为6位字符串，不足补0
            return fomoPayService.reversal(
                    stan
            );
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
