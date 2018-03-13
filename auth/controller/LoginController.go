package controller

import (
	"encoding/json"
	"github.com/astaxie/beego"
	log "github.com/sirupsen/logrus"
	"promise/auth/object/dto"
	"promise/auth/service"
	commonDto "promise/common/object/dto"
)

// LoginController Login controller
type LoginController struct {
	beego.Controller
}

// Post Post Login.
func (c *LoginController) Post() {
	log.Debug("POST Login request.")
	request := new(dto.PostLoginRequest)
	if err := json.Unmarshal(c.Ctx.Input.RequestBody, request); err != nil {
		log.Warn("Unmarshal() failed, error = ", err)
	}

	if session, messages := service.Login(request); messages != nil {
		c.Data["json"] = commonDto.MessagesToDto(messages)
		c.Ctx.Output.SetStatus(messages[0].StatusCode)
	} else {
		resp := new(dto.PostLoginResponse)
		resp.Load(session)
		c.Data["json"] = &resp
	}
	c.ServeJSON()
}
