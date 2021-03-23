/*
 *  Copyright (c) 2020-2021 Guo Limin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.x19990416.mxpaas.module.auth.shiro;

import com.github.x19990416.mxpaas.module.auth.domain.AuthUser;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ShiroRedisCacheManager implements CacheManager {
  private final RedisTemplate redisTemplate;

  @Override
  public <K, V> Cache<K, V> getCache(String s) throws CacheException {
    return new ShiroRedisCache<K, V>(redisTemplate, s);
  }

  public class ShiroRedisCache<K, V> implements Cache<K, V> {
    private RedisTemplate redisTemplate;
    private String cacheKey = "shiro_redis:";

    public ShiroRedisCache(RedisTemplate redisTemplate, String name) {
      this.redisTemplate = redisTemplate;
      cacheKey = cacheKey + name;
    }

    @Override
    public V get(K key) {
      BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);
      Object k = hashKey(key);
      return hash.get(k);
    }

    @Override
    public V put(K key, V value) {
      BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);
      Object k = hashKey(key);
      hash.put((K) k, value);
      return value;
    }

    @Override
    public V remove(K key) {
      BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);

      Object k = hashKey(key);
      V value = hash.get(k);
      hash.delete(k);
      return value;
    }

    @Override
    public void clear() {
      redisTemplate.delete(cacheKey + "*");
    }

    @Override
    public int size() {
      BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);
      return hash.size().intValue();
    }

    @Override
    public Set<K> keys() {
      BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);
      return hash.keys();
    }

    @Override
    public Collection<V> values() {
      BoundHashOperations<String, K, V> hash = redisTemplate.boundHashOps(cacheKey);
      return hash.values();
    }

    protected Object hashKey(K key) {
      // 此处很重要,如果key是登录凭证,那么这是访问用户的授权缓存;将登录凭证转为user对象,
      // 返回user的name属性做为hash key,否则会以user对象做为hash key,这样就不好清除指定用户的缓存了
      if (key instanceof PrincipalCollection) {
        PrincipalCollection pc = (PrincipalCollection) key;
        AuthUser user = (AuthUser) pc.getPrimaryPrincipal();
        return user.getId();
      } else if (key instanceof AuthUser) {
        AuthUser user = (AuthUser) key;
        return user.getId();
      }
      return key;
    }
  }
}
