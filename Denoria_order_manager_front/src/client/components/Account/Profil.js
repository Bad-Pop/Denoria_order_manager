import React, {Component} from 'react';
import {Tooltip, OverlayTrigger} from 'react-bootstrap';
import '../../assets/css/Profil.css';

class Profil extends Component {
    render() {
        const userPseudo = localStorage.getItem("user-pseudo");
        const avatarTooltip = (<Tooltip id="dashboardTooltip"><strong>{userPseudo}</strong></Tooltip>);
        return (
            <div>
                <h1>Mon profil</h1>
                <div className="page-hr"/>
                <div className="profil">
                    <div className="col-xs-12 col-sm-12 col-md-3 col-lg-3">
                        <OverlayTrigger placement="bottom" overlay={avatarTooltip}>
                            <img className="img-circle img-responsive img-thumbnail center-block"
                                 src={"http://cravatar.eu/helmavatar/" + userPseudo + "/210.png"}
                                 alt="User avatar"
                            />
                        </OverlayTrigger>
                    </div>
                    <div className="col-xs-12 col-sm-12 col-md-9 col-lg-9">
                        <div className="form-group no-margin">
                            <label className="control-label form-label">Mon adresse email</label>
                            <div className="input-group">
                        <span className="input-group-addon"><i className="fa fa-envelope"
                                                               aria-hidden="true"/></span>
                                <input type="email" className="form-control" id="profilMail" name="profilMail"
                                       ref={input => this.mail = input} placeholder="Ma nouvelle adresse email"
                                       value={localStorage.getItem("user-mail")} disabled={true}
                                />
                            </div>
                        </div>
                        <div className="form-group no-margin">
                            <label className="control-label form-label">Mon pseudo skype</label>
                            <div className="input-group">
                        <span className="input-group-addon"><i className="fa fa-skype"
                                                               aria-hidden="true"/></span>
                                <input type="text" className="form-control" id="profilSkype" name="profilSkype"
                                       ref={input => this.skype = input} placeholder="Mon nouveau compte skype"
                                       value={localStorage.getItem("user-skype")} disabled={true}
                                />
                            </div>
                        </div>
                        <div className="form-group no-margin">
                            <label className="control-label form-label">Mon avatar</label>
                            <div className="input-group">
                        <span className="input-group-addon"><i className="fa fa-address-card"
                                                               aria-hidden="true"/></span>
                                <input type="text" className="form-control" id="profilAvatar" name="profilAvatar"
                                       ref={input => this.skype = input} placeholder="Mon nouveau compte skype"
                                       value={"http://cravatar.eu/helmavatar/" + userPseudo + "/210.png"}
                                       disabled={true}
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}

export default Profil;