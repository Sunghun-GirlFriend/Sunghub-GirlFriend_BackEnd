package com.girlfriend.sunghun.domain.quest.presentation;

import com.girlfriend.sunghun.domain.quest.presentation.dto.request.ChangeStatusRequest;
import com.girlfriend.sunghun.domain.quest.presentation.dto.request.CreateQuestRequest;
import com.girlfriend.sunghun.domain.quest.presentation.dto.response.QuestListResponse;
import com.girlfriend.sunghun.domain.quest.service.QuestService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quest")
@RequiredArgsConstructor
public class QuestController {

    private final QuestService questService;

    @ApiOperation("갓생 퀘스트 생성")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public void createQuest(
            @RequestBody CreateQuestRequest request
    ) {
        questService.createQuest(request);
    }

    @ApiOperation("유저의 갓생 퀘스트 리스트 가져오기")
    @GetMapping("/list")
    public QuestListResponse getListByUser() {
        return questService.getListByUser();
    }

    @ApiOperation("갓생 퀘스트 상태 변경")
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/change/{quest-id}")
    public void changeStatus(
            @PathVariable("quest-id") Long id,
            @RequestBody ChangeStatusRequest request
    ) {
        questService.changeStatus(id, request);
    }

    @ApiOperation("갓생 퀘스트 완료 확인")
    @ResponseStatus(HttpStatus.CREATED)
    @PatchMapping("/check/{quest-id}")
    public void checkComplete(
            @PathVariable("quest-id") Long id
    ) {
        questService.checkComplete(id);
    }

}
