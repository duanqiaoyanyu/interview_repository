1. redisTemplate get 不存在的 key 返回的是 null, 而不是 '';
2. Json.parse 传空串调用 会返回 null
3. Json.parse 传 null 不会报错 也会返回 null 但是不建议这么操作
