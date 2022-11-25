package hello.tis.specificextensionblocking;


import com.fasterxml.jackson.core.JsonProcessingException;
import hello.tis.specificextensionblocking.api.dto.ExtensionResponses;
import hello.tis.specificextensionblocking.api.service.ExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 확장자를 관리할 수 있는 화면을 제공합니다.
 */
@Controller
@RequiredArgsConstructor
public class IndexController {

  private final ExtensionService extensionService;

  /**
   * 화면을 제공합니다. 전달되는 값은 고정 확장자 리스트와 커스텀 확장자 리스트입니다.
   *
   * @param model 화면에 전달될 모델입니다.
   * @return template.html
   * @throws JsonProcessingException 객체를 문자열로 만들지 못할 경우 문제가 발생합니다.
   */
  @GetMapping("index")
  public String index(Model model) throws JsonProcessingException {
    ExtensionResponses extensionResponses = extensionService.findAll();

    model.addAttribute("data", "hello");
    model.addAttribute("fixedExtensions", extensionResponses.getFixedExtensions());
    model.addAttribute("customExtensions",
        extensionResponses.getCustomExtensions().getCustomExtensionResponses());
    return "template";
  }
}
