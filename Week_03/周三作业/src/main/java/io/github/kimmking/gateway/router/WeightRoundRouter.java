package io.github.kimmking.gateway.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class WeightRoundRouter extends AbstractHttpEndpointRouter {
    Logger logger = LoggerFactory.getLogger(WeightRoundRouter.class);

    /**上次选择的服务器*/
    private int currentIndex = -1;
    /**当前调度的权值*/
    private int currentWeight = 0;
    /**最大权重*/
    private int maxWeight;
    /**权重的最大公约数*/
    private int gcdWeight;
    /**服务器数*/
    private int serverCount;

    public WeightRoundRouter(List<String> endpoints) {
        super(endpoints);
        maxWeight = greatestWeight(serverList);
        gcdWeight = greatestCommonDivisor(serverList);
        serverCount = serverList.size();
    }

    @Override
    public BackendServer route() {
        BackendServer server = getServer();
        System.out.println("当前路由服务器序号："+ serverList.indexOf(server));
        return server;
    }

    /*
     * 得到两值的最大公约数
     */
    public int greaterCommonDivisor(int a, int b){
        if(a % b == 0){
            return b;
        }else{
            return greaterCommonDivisor(b,a % b);
        }
    }
    /*
     * 得到list中所有权重的最大公约数，实际上是两两取最大公约数d，然后得到的d
     * 与下一个权重取最大公约数，直至遍历完
     */
    public int greatestCommonDivisor(List<BackendServer> servers){
        int divisor = 0;
        for(int index = 0, len = servers.size(); index < len - 1; index++){
            if(index ==0){
                divisor = greaterCommonDivisor(
                        servers.get(index).getWeight(), servers.get(index + 1).getWeight());
            }else{
                divisor = greaterCommonDivisor(divisor, servers.get(index).getWeight());
            }
        }
        return divisor;
    }

    /*
     * 得到list中的最大的权重
     */
    public int greatestWeight(List<BackendServer> servers){
        int weight = 0;
        for(BackendServer server : servers){
            if(weight < server.getWeight()){
                weight = server.getWeight();
            }
        }
        return weight;
    }

    /**
     *  算法流程：
     *  假设有一组服务器 S = {S0, S1, …, Sn-1}
     *  有相应的权重，变量currentIndex表示上次选择的服务器
     *  权值currentWeight初始化为0，currentIndex初始化为-1 ，当第一次的时候返回 权值取最大的那个服务器，
     *  通过权重的不断递减 寻找 适合的服务器返回
     */
    public BackendServer getServer() {
        while(true){
            currentIndex = (currentIndex + 1) % serverCount;
            if(currentIndex == 0){
                currentWeight = currentWeight - gcdWeight;
                if(currentWeight <= 0){
                    currentWeight = maxWeight;
                    if(currentWeight == 0){
                        return null;
                    }
                }
            }
            if(serverList.get(currentIndex).getWeight() >= currentWeight){
                return serverList.get(currentIndex);
            }
        }
    }


}
