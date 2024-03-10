package project.graduation.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import project.graduation.dto.MesengerDTO;
import project.graduation.dto.MessengerFormCreate;
import project.graduation.entity.Messenger;
import project.graduation.service.IMessengerService;

@RestController
@RequestMapping(value = "api/v1/messengers")
@CrossOrigin("*")
public class MessengerController {

	@Autowired
	private IMessengerService service;

	@GetMapping(value = "/userIds")
	public ResponseEntity<?> getAllUser() {
		List<Integer> arr = service.FindAllUserId();
		List<MesengerDTO> mess = new ArrayList<>();
		for (Integer ar : arr) {
			List<Messenger> mes = service.getListMessengerByUserId(ar);
			MesengerDTO dto = new MesengerDTO();
			if (mes.get(mes.size() - 1).getChatboxStatus().toString().equals("OFF")) {
				dto.setId(1);
				dto.setSenderName(mes.get(mes.size() - 1).getUser().getUserName());
				dto.setUserAvatar(mes.get(mes.size() - 1).getUser().getAvatarUrl());
			} else {
				dto.setId(0);
				dto.setSenderName(mes.get(mes.size() - 1).getUser().getUserName());
				dto.setUserAvatar(mes.get(mes.size() - 1).getUser().getAvatarUrl());
			}
//			dto.setId(mes.getUser().getId());
			dto.setMessage(mes.get(mes.size() - 1).getInboxContent());
			mess.add(dto);
		}
		Collections.reverse(mess);

		return new ResponseEntity<>(mess, HttpStatus.OK);
	}

	@GetMapping(value = "/{userName}")
	public ResponseEntity<?> getMessengerByUserName(@PathVariable(name = "userName") String userName) {
		List<Messenger> mess = service.getListMessengerByUserName(userName);
		List<MesengerDTO> arr = new ArrayList<>();
		for (Messenger mes : mess) {
			MesengerDTO dto = new MesengerDTO();
			if (mes.getChatboxStatus().toString().equals("OFF")) {
				dto.setId(1);
				dto.setSenderName("admin");
			} else {
				dto.setId(0);
				dto.setSenderName(mes.getUser().getUserName());
			}
//		dto.setId(mes.getUser().getId());
			dto.setMessage(mes.getInboxContent());
			arr.add(dto);
		}

		return new ResponseEntity<>(arr, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<?> createMessenger(@RequestBody MessengerFormCreate form) {
		service.createMessenger(form);
		return new ResponseEntity<String>("Create successfully!", HttpStatus.OK);
	}

}
