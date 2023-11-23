package cn.edu.tongji.diaryWriting.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DiaryWritingMapper<Diary> {
    //插入日记
    @Insert("INSERT INTO diary (user_id, create_date, title, content, cover_img, background_music, permission) VALUES (#{user_id}, #{create_date}, #{title}, #{content}, #{cover_img}, #{background_music}, #{permission})")
    void insertDiary(Diary diary);

    //删除日记
    @Delete("DELETE FROM diary WHERE id = #{id}")
    void deleteDiaryById(int id);

    //根据id查询日记
    @Select("SELECT * FROM diary WHERE id = #{id}")
    Diary getDiaryById(int id);

    //根据用户id查询该用户的所有id
    @Select("SELECT * FROM diary WHERE user_id = #{user_id}")
    List<Diary> getDiariesByUserId(int user_id);
}
