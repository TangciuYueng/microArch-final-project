package cn.edu.tongji.Emotions.repository;

import cn.edu.tongji.Emotions.model.MusicReference;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MusicReferenceRepository extends MongoRepository<MusicReference, String> {
    // 这里可以定义自定义的查询方法，Spring Data会自动实现基础的CRUD操作
}
