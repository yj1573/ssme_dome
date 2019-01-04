package com.yj.utils;

//import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 *
 *
 *
 */
public class DeptRelationUtil {
    private Set<Map.Entry<String, String>> set;
    private Map<String, String> map;
    private String root = "0";
    private List<String> list;

    //{"id":"pid"}
    public DeptRelationUtil(Map<String, String> map) {
        this.map = map;
        set = map.entrySet();
    }

    public static void main(String[] args) {
        String s = "{\"1\":\"全国\",\"2\":\"经销商\",\"3\":\"一汽大众\",\"4\":\"西南区\",\"5\":\"四川省\",\"6\":\"成都市\",\"7\":\"一汽大众安信经销商\",\"8\":\"金融方\",\"9\":\"北京市\",\"10\":\"西南区\",\"11\":\"四川省\",\"12\":\"审核部门\",\"13\":\"监管方\",\"14\":\"西南区\",\"15\":\"四川\",\"20\":\"测试经销商1\",\"21\":\"测试经销商2\",\"22\":\"审核部门2\",\"23\":\"四川金融方审核专员\",\"24\":\"四川金融方审核专员2\",\"34\":\"一汽大众安信经销商2\",\"35\":\"上海大众\",\"36\":\"华东区\",\"37\":\"上海市(省)\",\"38\":\"上海市\",\"39\":\"上海大众经销商1\",\"40\":\"上海大众经销商2\"}\n";
        String ids = "{\"1\":\"0\",\"2\":\"1\",\"3\":\"2\",\"4\":\"3\",\"5\":\"4\",\"6\":\"5\",\"7\":\"6\",\"8\":\"1\",\"9\":\"9\",\"10\":\"9\",\"11\":\"10\",\"12\":\"11\",\"13\":\"1\",\"14\":\"13\",\"15\":\"14\",\"20\":\"5\",\"21\":\"0\",\"22\":\"11\",\"23\":\"12\",\"24\":\"12\",\"34\":\"6\",\"35\":\"2\",\"36\":\"35\",\"37\":\"36\",\"38\":\"37\",\"39\":\"38\",\"40\":\"38\"}";
        Map<String, String> idMap = null;//JSON.parseObject(ids, HashMap.class);
        Map<String, String> map = null;//JSON.parseObject(s, HashMap.class);
        DeptRelationUtil dru = new DeptRelationUtil(idMap);

        //List<String> lists = Arrays.asList("7", "34", "39", "40");//
        List<String> lists = dru.getLastLayerIds("3");
        Map<Object, Object> treeMap = new HashMap<>();
        for (int j = 0; j < lists.size(); j++) {
            List<String> list = dru.getUpLayerPaths(lists.get(j));
            for (int i = list.size() - 1; i >= 1; i--) {
                dru.addTreeNode(treeMap, list.get(i), list.get(i - 1), map.get(list.get(i - 1)));
            }
            dru.addTreeNode(treeMap, list.get(0), lists.get(j), map.get(lists.get(j)));
        }
//        System.out.println(JSON.toJSONString(treeMap));

        /*
        Map<String, List<Map>> tree=dru.getDownLayerIds("3");
        System.out.println(JSON.toJSONString(tree));
        */

    }


    /**
     * 根据当前id查找子id集合
     * @param map
     * @param id
     */
    private void getDownLayerId(Map<String, List<Map>> map, String id) {
        for (Map.Entry<String, String> e : set) {
            if (e.getValue().equals(id)) {
                List list = map.get(id);
                if (list == null) {
                    list = new ArrayList<>();
                }
                Map<String, List<Map>> m = new HashMap<>();
                m.put(e.getKey(), new ArrayList<>());
                list.add(m);
                map.put(id, list);
                getDownLayerId(m, e.getKey());
            }
        }
    }

    /**
     * 根据id查询该id的最后一级id集合
     *
     * @param v
     */
    private void getLastLayerId(String v) {
        boolean boo = true;
        for (Map.Entry<String, String> e : set) {
            if (e.getValue().equals(v)) {
                boo = false;
                getLastLayerId(e.getKey());
            }
        }
        if (boo) {
            list.add(v);
        }
    }

    /**
     * 查找从当前id往上的id路径集合
     *
     * @param id
     */
    private void getUpLayerPath(String id) {
        String pid = map.get(id);
        while (pid != null && !pid.equals(root)) {
            list.add(pid);
            pid = map.get(pid);
        }
        list.add(root);
    }

    public void addTreeKeyNode(Map<Object, Object> map, Object pid, Object id, Object v) {
        Object p = map.get("id");
        if (p != null&&p.equals(pid)) {//已找到pid
            boolean boo = true;
            List<Map<Object, Object>> list = ((List<Map<Object, Object>>) map.get("children"));
            for (Map<Object, Object> m : list) {
                Object t = m.get("id");
                if (t != null && t.equals(id)) {//节点相同
                    boo = false;
                }
            }
            if (boo) {
                Map m = new HashMap<>();
                m.put("id",id);
                m.put("pid", pid);
                m.put("label", v);
                m.put("children", new ArrayList<>());
                list.add(m);
            }
        } else {
            if (map.size() != 0) {//进行迭代
                List<Map<Object, Object>> list = (List<Map<Object, Object>>) map.get("children");
                for (Map<Object, Object> m : list) {
                    addTreeKeyNode(m, pid, id, v);
                }
            } else {//初始化root
                map.put("id",id);
                map.put("pid", pid);
                map.put("label", v);
                map.put("children", new ArrayList<>());
            }
        }
    }


    public void addTreeNode(Map<Object, Object> map, Object pid, Object id, Object v) {
        Object p = map.get(pid);
        if (p != null) {//已找到pid
            boolean boo = true;
            List<Map<Object, Object>> list = ((List<Map<Object, Object>>) map.get(p));
            for (Map<Object, Object> m : list) {
                Object t = m.get(id);
                if (t != null && t.equals(v)) {//节点相同
                    boo = false;
                }
            }
            if (boo) {
                Map m = new HashMap<>();
                m.put(id, v);
                m.put("pid", pid);
                m.put(v, new ArrayList<>());
                list.add(m);
            }
        } else {
            if (map.size() != 0) {//进行迭代
                for (Map.Entry<Object, Object> e : map.entrySet()) {
                    if (e.getValue() instanceof List) {
                        List<Map<Object, Object>> list = (List<Map<Object, Object>>) e.getValue();
                        for (Map<Object, Object> m : list) {
                            addTreeNode(m, pid, id, v);
                        }
                    }
                }
            } else {//初始化root
                map.put(id, v);
                map.put("pid", pid);
                map.put(v, new ArrayList<>());
            }
        }
    }


    public Map<String, List<Map>> getDownLayerIds(String id){
        Map<String, List<Map>> tree = new HashMap<>();
        getDownLayerId(tree, id);
        return tree;
    }

    public List<String> getLastLayerIds(String v) {
        list = new ArrayList<>();
        getLastLayerId(v);
        return list;
    }

    public List<String> getUpLayerPaths(String v) {
        list = new ArrayList<>();
        getUpLayerPath(v);
        return list;
    }


}
