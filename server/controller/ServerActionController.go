package controller

import (
	commonDto "promise/common/object/dto"
	commonM "promise/common/object/model"
	m "promise/server/object/model"
	"promise/server/service"
	"promise/server/util"
	"strings"

	"github.com/astaxie/beego"
)

// ServerActionController Server action controller
type ServerActionController struct {
	beego.Controller
}

// Post Post method.
func (c *ServerActionController) Post() {
	action := c.Ctx.Input.Param(":action")
	id := c.Ctx.Input.Param(":id")
	beego.Trace("Post(), action = ", action, ", server ID = ", action, id)
	switch strings.ToLower(action) {
	case util.ServerActionRefresh:
		if resp, messages := service.RefreshServer(id); messages != nil {
			c.Data["json"] = commonDto.MessagesToDto(messages)
			c.Ctx.ResponseWriter.WriteHeader(messages[0].StatusCode)
		} else {
			c.Data["json"] = &resp
		}
	default:
		messages := []commonM.Message{}
		messages = append(messages, m.NewServerParameterError())
		c.Data["json"] = commonDto.MessagesToDto(messages)
		c.Ctx.ResponseWriter.WriteHeader((messages)[0].StatusCode)
	}
	c.ServeJSON()
}