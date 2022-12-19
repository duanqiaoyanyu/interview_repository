1. redisTemplate get 不存在的 key 返回的是 null, 而不是 '';
2. Json.parse 传空串调用 会返回 null
3. Json.parse 传 null 不会报错 也会返回 null 但是不建议这么操作
4. redisTemplate.increment(K key) 当 key 不存在时会初始化 0 并加 1, 最终返回 1, 不会报错. 
