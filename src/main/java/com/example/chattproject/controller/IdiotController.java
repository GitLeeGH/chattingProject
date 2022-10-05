//package com.example.chattproject.controller;
//
//
//@Controller
//@RequestMapping("/idiot")
//public class IdiotController {
//
//    private final IdiotService idiotService;
//    private final IdiotRepository idiotRepository;
//
//    public IdiotController(IdiotService idiotService, IdiotRepository idiotRepository) {
//        this.idiotService = idiotService;
//        this.idiotRepository = idiotRepository;
//    }
//
//    @GetMapping("/list")
//    public String idiotsPage(Model model) {
//        model.addAttribute("list", idiotService.getIdiots());
//        return "idiot/list";
//    }
//
//    @GetMapping("/detail/{idiotId}")
//    public String idiotDetailPage(@PathVariable String idiotId,
//                                  Model model) {
//        model.addAttribute("info", idiotRepository.findByIdiotId(idiotId));
//        return "idiot/detail";
//    }
//
//    @GetMapping("/{idiotId}/board/{boardId}")
//    public String createIdiotPageFromBoard(Model model,
//                                           @PathVariable String idiotId,
//                                           @PathVariable String boardId) {
//        model.addAttribute("idiotId", idiotId);
//        model.addAttribute("type", "board");
//        model.addAttribute("typeId", boardId);
//        return "idiot/idiot-create";
//    }
//
//    @GetMapping("/{idiotId}/comment/{commentId}")
//    public String createIdiotPageFromComment(Model model,
//                                             @PathVariable String idiotId,
//                                             @PathVariable String commentId) {
//        model.addAttribute("idiotId", idiotId);
//        model.addAttribute("type", "comment");
//        model.addAttribute("typeId", commentId);
//        return "idiot/idiot-create";
//    }
//
//    @PostMapping("/{idiotId}/{type}/{typeId}")
//    public String addIdiot(RedirectAttributes attributes,
//                           @PathVariable String idiotId,
//                           @PathVariable String type,
//                           @PathVariable String typeId,
//                           @Valid IdiotDto IdiotDto) {
//        if (idiotService.addIdiot(idiotId, type, typeId, IdiotDto)) {
//            attributes.addFlashAttribute("message", "신고 완료");
//        } else {
//            attributes.addFlashAttribute("message", "신고 오류");
//        }
//        if (type.equals("comment")) {
//            return "redirect:/board/detail/" + idiotService.getBoardId(typeId);
//        } else {
//            return "redirect:/board/detail/" + typeId;
//        }
//    }
//}