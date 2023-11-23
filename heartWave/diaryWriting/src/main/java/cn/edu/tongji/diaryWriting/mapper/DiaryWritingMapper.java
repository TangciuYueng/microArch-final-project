package cn.edu.tongji.diaryWriting.mapper;

import cn.edu.tongji.diaryWriting.dto.addDiaryWritingRequest;
import cn.edu.tongji.diaryWriting.model.diary;
import org.apache.ibatis.annotations.*;

import java.util.List;

//新建时候要选择interface 方法就是对应数据库的增删改查
@Mapper
public interface DiaryWritingMapper {
    //插入日记
    @Insert("INSERT INTO diary (user_id, create_date, title, content, cover_img, background_music, permission) VALUES (#{user_id}, #{create_date}, #{title}, #{content}, #{cover_img}, #{background_music}, #{permission})")
    void insertDiary(addDiaryWritingRequest Diary);

    //删除日记
    @Delete("DELETE FROM diary WHERE id = #{id} AND user_id=#{userId}")
    void deleteDiaryById(int id,int userId);

    //根据id查询日记
    @Select("SELECT * FROM diary WHERE id = #{id} AND user_id=#{userId}")
    diary getDiaryById(int id,int userId);

    //根据用户id查询该用户的所有id
    @Select("SELECT * FROM diary WHERE user_id = #{user_id}")
    List<diary> getDiariesByUserId(int user_id);
}
