package com.sweet.plugin.cost;

import java.util.concurrent.CopyOnWriteArrayList;

public class CostPlugin {

    private static final CopyOnWriteArrayList<OnMethodCostListener> callbacks = new CopyOnWriteArrayList<>();

    public static void addListener(OnMethodCostListener listener) {
        callbacks.add(listener);
    }

    public static void onMethodExit(String clazzName, String funName, Long cost) {
        for (int i = 0; i < callbacks.size(); i++) {
            try {
                callbacks.get(i).onMethodCost(clazzName, funName, cost);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}