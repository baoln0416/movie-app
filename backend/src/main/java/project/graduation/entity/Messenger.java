package project.graduation.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "`messenger`")
@Getter
@Setter
@NoArgsConstructor
public class Messenger implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "`chatbox_id`")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int chatboxId;

	@ManyToOne
	@JoinColumn(name = "`user_id`")
	private User user;

	@Column(name = "`chatbox_status`", nullable = false)
	@Enumerated(EnumType.STRING)
	private ChatboxStatus chatboxStatus;

	@Column(name = "`inbox_content`", nullable = false)
	private String inboxContent;

	public Messenger(User user, ChatboxStatus chatboxStatus, String inboxContent) {
		super();
		this.user = user;
		this.chatboxStatus = chatboxStatus;
		this.inboxContent = inboxContent;
	}

	public enum ChatboxStatus {
		ON, OFF
	}
}
