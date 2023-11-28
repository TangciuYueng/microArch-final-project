package cn.edu.tongji.diaryWriting.mapper;

import cn.edu.tongji.diaryWriting.dto.AddDiaryWritingRequest;
import cn.edu.tongji.diaryWriting.model.Diary;
import org.apache.ibatis.annotations.*;

import java.util.List;

//新建时候要选择interface 方法就是对应数据库的增删改查
@Mapper
public interface DiaryWritingMapper {
    //插入日记
    @Insert("INSERT INTO diary (id, user_id, create_date, title, content, cover_img, background_music, permission) VALUES (#{id}, #{userId}, #{createDate}, #{title}, #{content}, #{coverImg}, #{backgroundMusic}, #{permission})")
    void insertDiary(AddDiaryWritingRequest Diary);

    //删除日记
    @Delete("DELETE FROM diary WHERE id = #{id}")
    void deleteDiaryById(int id);

    //根据id查询日记
    @Select("SELECT * FROM diary WHERE id = #{id} AND user_id=#{userId}")
    Diary getDiaryById(int id, int userId);

    //根据用户id查询该用户的所有日记
    @Select("SELECT * FROM diary WHERE user_id = #{userId}")
    List<Diary> getDiariesByUserId(int userId);

    //获得目前表中一共有多少数据，用来生成diary表中数据的id
    @Select("SELECT COUNT(*) FROM diary")
    int getDiaryCount();
}
