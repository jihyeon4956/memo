package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;

import java.util.List;

public class MemoService {
    //    private final JdbcTemplate jdbcTemplate;    // 데이터베이스
    // 이 필드는  new MemoRepository(jdbcTemplate);를 구현할때 생성자 파라미터로 쓰려고 선언했음
    // 그러나 코드의 중복이기 때문에 아예 다른 방법으로 구현할거고 그럼 이 코드는 필요없음

    // ★ IoC, DI 에 따라 코드 재생성
    private final MemoRepository memoRepository; // 아래에서 사용할 수 있게 생성한다.

    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
        // 메모 서비스가 생성자를 통해서 생성될 때 파라미터로 jdbcTemplate를 받아오고 만든다.
//        this.jdbcTemplate = jdbcTemplate;
    }

    // @PostMapper에 사용된 서비스
    public MemoResponseDto creatMemo(MemoRequestDto requestDto) {
        // @PostMapping에 추가된 creatMemo메서드 구현하기
        // 반환타입: MemoResponseDto, 파라미터에도 requestDto가 들어옴
        Memo memo = new Memo(requestDto);
        // requestDto는 username과 contants만 가지고 있기 때문에 기본생성자 이외에 생성자로 지정해야함
        // Alt+Enter로 생성자 생성


        // ★★DB 저장
//        MemoRepository memoRepository = new MemoRepository(jdbcTemplate); // Memorepository에서 생성자 만들기
        Memo saveMemo = memoRepository.save(memo); // 저장 메서드를 생성 후 memo를 저장하기, 이걸 Memo로 반환함
        // 이렇게 진행시 MemoRepository에 save()메서드가 만들어지고 파라미터로는 저장할 memo객체를 전달해준다.반환은 Memo로!


        // Entity -> ResponseDto로 변환을 진행함
        MemoResponseDto memoResponseDto = new MemoResponseDto(saveMemo);  // 강의에서는 memo로 받았는데 saveMemo여야함

        return memoResponseDto;
    }

    // 메모 조회하기 @GetMapping
    public List<MemoResponseDto> getMemos() {
        // DB 조회
//        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        return memoRepository.findAll();
    }


    // 메모 변경하기   @PutMapping
    public Long getupdateMemo(Long id, MemoRequestDto requestDto) {
//        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if(memo != null) {
            // memo 내용 수정
            memoRepository.update(id, requestDto);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


    // 메모 삭제하기  @DeleteMapping
    public Long deleteService(Long id) {
//        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if(memo != null) {
            // memo 삭제
            memoRepository.delete(id);

            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
