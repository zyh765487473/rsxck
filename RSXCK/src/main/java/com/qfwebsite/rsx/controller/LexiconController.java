package com.qfwebsite.rsx.controller;

import com.ksyun.ks3.utils.StringUtils;
import com.qfwebsite.rsx.error.RequestFailedException;
import com.qfwebsite.rsx.request.*;
import com.qfwebsite.rsx.service.AccountService;
import com.qfwebsite.rsx.service.LexiconService;
import com.qfwebsite.rsx.utils.CryptoUtils;
import com.qfwebsite.rsx.utils.HttpCode;
import com.qfwebsite.rsx.utils.ResponseUtils;
import com.qfwebsite.rsx.utils.SimpleResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Api(tags = "词库查询接口")
@RestController
public class LexiconController {
    private static final Logger logger = LoggerFactory.getLogger(LexiconController.class);

    @Value("${login.md5_str}")
    private String MD5_STR;

    @Autowired
    private LexiconService lexiconService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/lexicon/select/brandName")
    public SimpleResponse selectByBrandName(@RequestParam("brandName") String brandName, @RequestParam("nameId") Integer nameId, @RequestParam("token") String token, @RequestParam("md5") String md5) {
        try {
            // 1. 校验请求参数是否正确
            if (StringUtils.isBlank(token) || null == nameId || StringUtils.isBlank(md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (StringUtils.isBlank(brandName)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "品牌词不能为空");
            }
            if (!parameterVerification(brandName + token, md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            accountService.loginVerification(token, nameId);
            String lowerCase = brandName.replaceAll("\\s", "").toLowerCase();
            return ResponseUtils.createOkResponse(lexiconService.findByBrandName(lowerCase));
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    @GetMapping("/lexicon/select/nameId")
    public SimpleResponse selectByNameId(@RequestParam("nameId") Integer nameId, @RequestParam("token") String token, @RequestParam("md5") String md5) {
        try {
            // 1. 校验请求参数是否正确
            if (StringUtils.isBlank(token) || null == nameId || StringUtils.isBlank(md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (!parameterVerification(nameId + token, md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }

            accountService.loginVerification(token, nameId);

            return ResponseUtils.createOkResponse(lexiconService.findByNameId(nameId));
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    @GetMapping("/lexicon/select/general")
    public SimpleResponse selectGeneral(@RequestParam("nameId") Integer nameId, @RequestParam("token") String token, @RequestParam("md5") String md5) {
        try {
            // 1. 校验请求参数是否正确
            if (StringUtils.isBlank(token) || null == nameId || StringUtils.isBlank(md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (!parameterVerification(nameId + token, md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (1 != (nameId)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            accountService.loginVerification(token, nameId);

            return ResponseUtils.createOkResponse(lexiconService.getGeneral());
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    @GetMapping("/lexicon/select/loss")
    public SimpleResponse selectLoss(@RequestParam("nameId") Integer nameId, @RequestParam("token") String token, @RequestParam("md5") String md5) {
        try {
            // 1. 校验请求参数是否正确
            if (StringUtils.isBlank(token) || null == nameId || StringUtils.isBlank(md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (!parameterVerification(nameId + token, md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            accountService.loginVerification(token, nameId);

            return ResponseUtils.createOkResponse(lexiconService.getLoss());
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    @PostMapping("/lexicon/update/general/admin")
    public SimpleResponse updateGeneral(@RequestBody @Valid LexiconUpdateAdminRequest param) {
        try {
            // 1. 校验请求参数是否正确
            if (StringUtils.isBlank(param.getToken()) || null == param.getNameId() || StringUtils.isBlank(param.getMd5())) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (!parameterVerification(param.getNameId() + param.getToken(), param.getMd5())) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (1 != (param.getNameId())) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            accountService.loginVerification(param.getToken(), param.getNameId());

            lexiconService.updateLexiconAdmin(param.getId(), param.getFlow(), param.getaNameId());
            return ResponseUtils.createOkResponse();
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    @PostMapping("/lexicon/update/loss/admin")
    public SimpleResponse updateGeneral(@RequestBody @Valid LexiconUpdateLoseAdminRequest param) {
        try {
            // 1. 校验请求参数是否正确
            if (StringUtils.isBlank(param.getToken()) || null == param.getNameId() || StringUtils.isBlank(param.getMd5())) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (!parameterVerification(param.getNameId() + param.getToken(), param.getMd5())) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (1 != (param.getNameId())) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            accountService.loginVerification(param.getToken(), param.getNameId());

            lexiconService.updateLexiconLossAdmin(param.getId());
            return ResponseUtils.createOkResponse();
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    @PostMapping("/lexicon/loss/apply")
    public SimpleResponse applyLoss(@RequestBody @Valid LossLexiconRequest param) {
        try {
            // 1. 校验请求参数是否正确
            if (StringUtils.isBlank(param.getToken()) || null == param.getNameId() || StringUtils.isBlank(param.getMd5())) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (!parameterVerification(param.getNameId() + param.getToken(), param.getMd5())) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            accountService.loginVerification(param.getToken(), param.getNameId());

            lexiconService.LossLexiconApply(param.getId(), param.getNameId(), param.getFlow());
            return ResponseUtils.createOkResponse();
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    @GetMapping("/lexicon/select/nameId/brandName")
    public SimpleResponse selectByNameId(@RequestParam("nameId") Integer nameId, @RequestParam("token") String token, @RequestParam("brandName") String brandName, @RequestParam("md5") String md5) {
        try {
            // 1. 校验请求参数是否正确
            if (StringUtils.isBlank(token) || null == nameId || StringUtils.isBlank(md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (!parameterVerification(nameId + token, md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }

            accountService.loginVerification(token, nameId);
            if (StringUtils.isBlank(brandName)) {
                return ResponseUtils.createOkResponse(lexiconService.findByNameId(nameId));
            }
            String lowerCase = brandName.replaceAll("\\s", "").toLowerCase();
            return ResponseUtils.createOkResponse(lexiconService.findByBrandNameAndNameId(nameId, lowerCase));
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    @GetMapping("/lexicon/select/nameId/state")
    public SimpleResponse selectByNameId(@RequestParam("nameId") Integer nameId, @RequestParam("token") String token, @RequestParam("state") Integer state, @RequestParam("md5") String md5) {
        try {
            // 1. 校验请求参数是否正确
            if (StringUtils.isBlank(token) || null == nameId || StringUtils.isBlank(md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }
            if (!parameterVerification(nameId + token, md5)) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }

            accountService.loginVerification(token, nameId);
            if (null != state) {
                return ResponseUtils.createOkResponse(lexiconService.findByNameIdAndState(nameId, state));
            }
            return ResponseUtils.createOkResponse();
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    @PostMapping("/lexicon/add")
    public SimpleResponse add(@RequestBody @Valid LexiconRequest param) {
        try {
            // 1. 校验请求参数是否正确
            if (!parameterVerification(param.getBrandName() + param.getNameId() + param.getFlow(), param.getMd5())) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }

            // 2. 账号验证
            accountService.loginVerification(param.getToken(), param.getNameId());

            // 3. 添加
            lexiconService.saveLexicon(param.getBrandName(), param.getCompleteWord(), param.getIndependentStation(), param.getFlow(), param.getNameId(), param.getCountry());
            return ResponseUtils.createOkResponse();
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    @PostMapping("/lexicon/update")
    public SimpleResponse update(@RequestBody LexiconUpdateRequest param) {
        try {
            // 1. 校验请求参数是否正确
            if (!parameterVerification(param.getToken() + param.getNameId() + param.getType(), param.getMd5())) {
                return ResponseUtils.createOkResponse(HttpCode.PARAMS_INVALID, "参数错误，请联系管理员");
            }

            // 2. 账号验证
            accountService.loginVerification(param.getToken(), param.getNameId());

            // 3. 状态更新
            lexiconService.updateLexicon(param);
            return ResponseUtils.createOkResponse();
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    @PostMapping("/lexicon/update/data")
    @ApiParam(value = "要上传的文件", required = true)
    public SimpleResponse updateData(@RequestParam("file") MultipartFile file) {
        try {
            lexiconService.saveLexicon(file);
            return ResponseUtils.createOkResponse();
        } catch (RequestFailedException e) {
            return ResponseUtils.createOkResponse(e.getCode(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtils.createOkResponse(HttpCode.INNER_ERROR, "内部错误，请联系管理员");
        }
    }

    /**
     * 参数校验
     */
    public boolean parameterVerification(String data, String md5) {
        String str = data + MD5_STR;
        String pa = CryptoUtils.MD5(str);
        logger.info("str:{}", str);
        logger.info("pa:{}", pa);
        return pa.equals(md5);
    }
}
