package com.qfwebsite.rsx.service;

import com.ksyun.ks3.utils.StringUtils;
import com.qfwebsite.rsx.bean.Account;
import com.qfwebsite.rsx.bean.Lexicon;
import com.qfwebsite.rsx.dao.AccountRepository;
import com.qfwebsite.rsx.dao.LexiconRepository;
import com.qfwebsite.rsx.error.RequestFailedException;
import com.qfwebsite.rsx.request.LexiconUpdateRequest;
import com.qfwebsite.rsx.response.LexiconResponse;
import com.qfwebsite.rsx.utils.HttpCode;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Zhuangyuehui
 * @Date 2022.12.5
 */
@Service
public class LexiconService {
    private static final Logger logger = LoggerFactory.getLogger(LexiconService.class);

    @Autowired
    private LexiconRepository lexiconRepository;

    @Autowired
    private AccountRepository accountRepository;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public List<LexiconResponse> findByBrandName(String brandName) {
        List<LexiconResponse> res = new ArrayList<>();
        List<Lexicon> lexicon = lexiconRepository.findByBrandNameHand(brandName);
        if (!lexicon.isEmpty()) {
            for (Lexicon a : lexicon) {
                Integer nameId = a.getNameId();
                Account account = accountRepository.findByNameId(nameId);
                LexiconResponse response = new LexiconResponse();
                BeanUtils.copyProperties(a, response);
                response.setName(account.getName());
                response = handleData(response, a);
                res.add(response);
            }
        }
        return res;
    }

    public List<LexiconResponse> findByNameId(Integer nameId) {
        List<LexiconResponse> res = new ArrayList<>();
        List<Lexicon> lexicon = lexiconRepository.findByNameId(nameId);
        if (!lexicon.isEmpty()) {
            Account account = accountRepository.findByNameId(nameId);
            for (Lexicon a : lexicon) {
                if (a.getGeneral() != Lexicon.YES_GENERAL) {
                    LexiconResponse response = new LexiconResponse();
                    BeanUtils.copyProperties(a, response);
                    response.setName(account.getName());
                    response = handleData(response, a);
                    res.add(response);
                }
            }
        }
        return res;
    }

    public List<LexiconResponse> findByNameIdAndState(Integer nameId, Integer state) {
        List<LexiconResponse> res = new ArrayList<>();
        List<Lexicon> lexicon = lexiconRepository.findByNameIdAndState(nameId, state);
        if (!lexicon.isEmpty()) {
            Account account = accountRepository.findByNameId(nameId);
            for (Lexicon a : lexicon) {
                if (a.getGeneral() != Lexicon.YES_GENERAL) {
                    LexiconResponse response = new LexiconResponse();
                    BeanUtils.copyProperties(a, response);
                    response.setName(account.getName());
                    response = handleData(response, a);
                    res.add(response);
                }
            }
        }
        return res;
    }

    public List<LexiconResponse> findByBrandNameAndNameId(Integer nameId, String brandName) {
        List<LexiconResponse> res = new ArrayList<>();
        List<Lexicon> lexicon = lexiconRepository.findByBrandNameHandAndNameId(brandName.trim().toLowerCase(), nameId);
        if (!lexicon.isEmpty()) {
            Account account = accountRepository.findByNameId(nameId);
            for (Lexicon a : lexicon) {
                if (a.getGeneral() != Lexicon.YES_GENERAL) {
                    LexiconResponse response = new LexiconResponse();
                    BeanUtils.copyProperties(a, response);
                    response.setName(account.getName());
                    response = handleData(response, a);
                    res.add(response);
                }
            }
        }
        return res;
    }

    // 有效期内的变更
    public void updateLexicon(LexiconUpdateRequest param) {
        Lexicon lexicon = lexiconRepository.findById(param.getId()).get();
        if (null == lexicon || StringUtils.isBlank(lexicon.getBrandName())) {
            logger.warn("updateLexicon error id:{}", param.getId());
        } else {
            // 3种情况
            // 1：是否在公池内，2：是否过期， 3：是否是同一个组的人对其进行操作
            if (lexicon.getGeneral() == Lexicon.YES_GENERAL) {
                logger.warn("updateLexicon General error id:{}, nameId:{}", param.getId(), param.getNameId());
                // 公池产品
                throw new RequestFailedException(HttpCode.ACCOUNT_NOT_EXISTENT, "非常遗憾，属于公词产品，无权修改");
            }

            if (lexicon.getValidity().getTime() < new Date().getTime() && !param.getType().equals("2")) {
                logger.warn("updateLexicon Validity error id:{}, nameId:{}", param.getId(), param.getNameId());
                // 已过有效期
                throw new RequestFailedException(HttpCode.VALIDITY_TIME_INVALID, "非常遗憾，已经过了有效期，无权修改");
            }

            if (lexicon.getNameId() != param.getNameId()) {
                logger.warn("updateLexicon NameId error id:{}, nameId:{}", param.getId(), param.getNameId());
                // 归属权
                throw new RequestFailedException(HttpCode.GENERAL_INVALID, "非常遗憾，这个产品不属于你，无权修改");
            }

            if (param.getType().equals("0")) {
                // 上架状态更新
                if (lexicon.getState() == Lexicon.YES_STATE) {
                    logger.warn("updateLexicon NameId error id:{}, nameId:{}", param.getId(), param.getNameId());
                    throw new RequestFailedException(HttpCode.VALIDITY_UP_STATUS, "词的状态已经是已上架状态，请勿重复更新");
                }
                if (StringUtils.isBlank(param.getLink())) {
                    // 抛异常
                    throw new RequestFailedException(HttpCode.VALIDITY_LINK, "链接不能为空");
                }
                lexicon.setLink(param.getLink());
                lexicon.setState(Lexicon.YES_STATE);
            } else if (param.getType().equals("1")) {
                // 品牌注册状态更新
                if (lexicon.getRegistrationStatus().equals("已注册")) {
                    logger.warn("updateLexicon NameId error id:{}, nameId:{}", param.getId(), param.getNameId());
                    throw new RequestFailedException(HttpCode.VALIDITY_REGISTRATION_STATUS, "词的状态已经是注册状态，请勿重复更新");
                }
                lexicon.setRegistrationStatus("已注册");
            } else if (param.getType().equals("2")) {
                // 备注修改
                lexicon.setRemarks(param.getRemarks());
            } else {
                throw new RequestFailedException(HttpCode.INNER_ERROR, "更新状态有误，请联系管理员");
            }
            lexicon.setUpdateTime(new Date());
            lexiconRepository.save(lexicon);
        }
    }

    // admin的分配变更
    public void updateLexiconAdmin(Long id, Integer flow, Integer nameId) {
        Lexicon lexicon = lexiconRepository.findById(id).get();
        if (null == lexicon || StringUtils.isBlank(lexicon.getBrandName())) {
            logger.warn("updateLexiconAdmin error id:{}", id);
        } else {
            if (lexicon.getGeneral() == Lexicon.NOT_GENERAL) {
                throw new RequestFailedException(HttpCode.GEN_ERROR, "不是公库数据，请勿重复分配");
            }
            lexicon.setNameId(nameId);
            // 计算有效期
            lexicon.setFlow(flow);
            lexicon.setValidity(calculateValidity(flow));
            lexicon.setUpdateTime(new Date());
            lexicon.setState(Lexicon.NOT_STATE);
            lexicon.setGeneral(Lexicon.NOT_GENERAL);
            lexiconRepository.save(lexicon);
        }
    }

    public List<Lexicon> getGeneral() {
        return lexiconRepository.findByGeneral(Lexicon.YES_GENERAL);
    }

    public void saveLexicon(String brandName, String completeWord, String independentStation, Integer flow, Integer nameId, String country) {
        Lexicon lexicon = new Lexicon();
        lexicon.setCreateTime(new Date());
        lexicon.setFlow(flow);
        lexicon.setState(Lexicon.NOT_STATE);
        lexicon.setBrandName(brandName);
        lexicon.setCompleteWord(completeWord);
        lexicon.setIndependentStation(independentStation);
        lexicon.setGeneral(Lexicon.NOT_GENERAL);
        lexicon.setNameId(nameId);
        lexicon.setCountry(country);
        // 查询键（主要是去除品牌名的空格以及去除大小写）
        String aa = brandName.replaceAll("\\s", "").toLowerCase();
        lexicon.setBrandNameHand(aa);
        // 计算有效期
        lexicon.setValidity(calculateValidity(flow));
        lexicon.setRegistrationStatus("未注册");
        lexiconRepository.save(lexicon);
    }

    public void saveLexicon(MultipartFile file) throws IOException {
        File temp = File.createTempFile("temp", "");
        FileOutputStream fileOutputStream = new FileOutputStream(temp);
        fileOutputStream.write(file.getBytes());

        FileInputStream inputStream = new FileInputStream(temp);

        Workbook workbook = WorkbookFactory.create(inputStream);
        // 获取第一个工作表
        Sheet one = workbook.getSheetAt(0);
        // 获取行数
        int num = one.getLastRowNum() + 1;
        // 一行一行遍历
        for (int i = 1; i < num; i++) {
            Row row = one.getRow(i);
            // 遍历每一列
            String brandName = row.getCell(0).getStringCellValue();
            String wanzhengci = row.getCell(1).getStringCellValue();

            // 独立站可能为空
            Cell cell3 = row.getCell(2);
            String dulizhan = "";
            if (null != cell3) {
                dulizhan = cell3.getStringCellValue();
            }

            // 流量
            Cell cell = row.getCell(3);
            String liuliang = "";
            if (null != cell) {
                if (cell.getCellType() == CellType.NUMERIC) {
                    double numericCellValue = cell.getNumericCellValue();
                    int numericCellValue1 = (int) numericCellValue;
                    liuliang = String.valueOf(numericCellValue1);
                } else {
                    liuliang = cell.getStringCellValue();
                }
            }

            Cell cell1 = row.getCell(4);
            double numericCellValue = cell1.getNumericCellValue();
            int numericCellValue1 = (int) numericCellValue;
            String shangjia = String.valueOf(numericCellValue1);

            String zhuce = row.getCell(5).getStringCellValue();

            String shangjialianjie = "";
            // 上架链接
            Cell cell4 = row.getCell(6);
            if (null != cell4) {
                shangjialianjie = cell4.getStringCellValue();
            }

            String guojia = row.getCell(7).getStringCellValue();


            Cell cell2 = row.getCell(8);
            double numericCellValue2 = cell2.getNumericCellValue();
            int numericCellValue21 = (int) numericCellValue2;
            String nameId = String.valueOf(numericCellValue21);


            Lexicon lexicon = new Lexicon();
            lexicon.setBrandName(brandName);// 品牌名
            String aa = brandName.replaceAll("\\s", "").toLowerCase();
            lexicon.setBrandNameHand(aa); // 搜索键
            lexicon.setCompleteWord(wanzhengci);// 完整词
            lexicon.setIndependentStation(dulizhan);// 独立站
            //流量
            if (StringUtils.isBlank(liuliang)) {
                if (zhuce.equals("未注册")) {
                    lexicon.setFlow(9999999);
                } else {
                    // 已注册
                    lexicon.setFlow(20000);
                }
            } else {
                lexicon.setFlow(Integer.valueOf(liuliang));
            }
            lexicon.setState(Integer.valueOf(shangjia));//上架状态
            lexicon.setRegistrationStatus(zhuce);// 注册状态
            lexicon.setLink(shangjialianjie);// 上架链接
            lexicon.setCountry(guojia);//国家
            lexicon.setNameId(Integer.valueOf(nameId));//组ID

            // 创建时间
            lexicon.setCreateTime(new Date());
            // 公词状态
            lexicon.setGeneral(Lexicon.NOT_GENERAL);
            // 有效期
            if (lexicon.getState() == Lexicon.NOT_STATE && lexicon.getRegistrationStatus().equals("未注册") && StringUtils.isBlank(lexicon.getLink())) {
                Date v = calculateValidity(lexicon.getFlow());
                lexicon.setValidity(v);
            }
            lexiconRepository.save(lexicon);
        }
        temp.delete();
    }

    private Date calculateValidity(Integer flow) {
        Date date = new Date();
        if (flow > 100000) {
            // 14天
            date.setTime(date.getTime() + 1000 * 60 * 60 * 24 * 14);
        } else {
            // 7天
            date.setTime(date.getTime() + 1000 * 60 * 60 * 24 * 7);
        }
        return date;
    }

    private LexiconResponse handleData(LexiconResponse response, Lexicon a) {
        response.setCreateTime(simpleDateFormat.format(a.getCreateTime()));
        if (null != a.getValidity()) {
            response.setValidity(simpleDateFormat.format(a.getValidity()));
        }
        return response;
    }
}
