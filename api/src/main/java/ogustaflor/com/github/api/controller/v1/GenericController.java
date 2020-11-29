package ogustaflor.com.github.api.controller.v1;

import java.util.HashMap;
import java.util.Map;

public class GenericController {
	
	public Map<String, Object> criaObjetoComMensagem(String mensagem) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", mensagem);
		return body;
	}
	
}
