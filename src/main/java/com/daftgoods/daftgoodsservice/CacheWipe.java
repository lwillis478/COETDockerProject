package com.daftgoods.daftgoodsservice;

import com.daftgoods.daftgoodsservice.UserLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Lazy(false)
@Component
public class CacheWipe {
    @Autowired
    private Logger logger;

    @CacheEvict(value = "itemcache", allEntries = true)
    @Scheduled(initialDelay = 5000, fixedDelay = 5000)
    public void wipeCache()
    {
        logger.info("Cleared item cache");
    }
}
