package com.qfwebsite.rsx.fixed;


import com.qfwebsite.rsx.bean.Lexicon;
import com.qfwebsite.rsx.dao.LexiconRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ExpiredTasks {

    @Autowired
    private LexiconRepository lexiconRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExpiredTasks.class);

    @Scheduled(fixedRate = 60 * 1000)
    public void wordManagement() {
        // 获取未上架数据
        List<Lexicon> notState = lexiconRepository.findByStateAndRegistrationStatusAndGeneral(Lexicon.NOT_STATE, "未注册", Lexicon.NOT_GENERAL);
        if (null != notState && !notState.isEmpty()) {
            Date date = new Date();
            long time = date.getTime();
            notState.forEach(a -> {
                Date validity = a.getValidity();
                if (null != validity) {
                    long l = validity.getTime();
                    if (time > l) {
                        // 当前时间大于有效期，说明已过期
                        a.setGeneral(Lexicon.YES_GENERAL);
                        a.setUpdateTime(new Date());
                        lexiconRepository.save(a);
                    }
                } else {
                    // 这样的产品理论上属于公司。随时上下架
                }
            });
        }
    }
}
