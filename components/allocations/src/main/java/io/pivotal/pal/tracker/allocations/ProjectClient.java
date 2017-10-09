package io.pivotal.pal.tracker.allocations;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ProjectClient {

    private final RestOperations restOperations;
    private final String registrationServerEndpoint;
    private ConcurrentHashMap<Long, ProjectInfo> cacheMap = new ConcurrentHashMap<Long, ProjectInfo>();
//    @Autowired
//    private RedisTemplate redisTemplate;

    public ProjectClient(RestOperations restOperations, String registrationServerEndpoint) {
        this.restOperations= restOperations;
        this.registrationServerEndpoint = registrationServerEndpoint;
    }


    @HystrixCommand(fallbackMethod = "getProjectFromCache")
    public ProjectInfo getProject(long projectId) {
        ProjectInfo info = restOperations.getForObject(registrationServerEndpoint + "/projects/" + projectId, ProjectInfo.class);
        cacheMap.put(projectId, info);
//        redisTemplate.boundListOps(projectId).leftPush(info);
//        redisTemplate.opsForValue().set(projectId,info);
        return info;
    }

    public ProjectInfo getProjectFromCache(long projectId)
    {
        return cacheMap.get(projectId);
//        return (ProjectInfo) redisTemplate.opsForValue().get(projectId);
    }
}
