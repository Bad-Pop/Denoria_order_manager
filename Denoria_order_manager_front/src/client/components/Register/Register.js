import React, {Component} from 'react';
import {ajaxSetup, alertOptions, API_URL} from "../../../configuration/Configuration";
import AlertContainer from 'react-alert';
import {Link} from 'react-router-dom';
import $ from 'jquery';

import '../../assets/css/Register.css';

class Register extends Component {

    state = {
        user: {
            pseudo: "",
            mail: "",
            password: "",
            avatar_link: "",
            token: "",
            skype: "",
            active: false
        },
        pseudo: "",
        mail: "",
        password: "",
        confirm_password: "",
        skype: ""
    };

    constructor(props){
        super(props);
        this.ctx = ajaxSetup();
        this.alertOptions = alertOptions;
        this.setStatePromise = this.setStatePromise.bind(this);
        this.updatePseudo = this.updatePseudo.bind(this);
        this.updateMail = this.updateMail.bind(this);
        this.updatePassword = this.updatePassword.bind(this);
        this.updateConfirmPassword = this.updateConfirmPassword.bind(this);
        this.updateSkype = this.updateSkype.bind(this);
        this.register = this.register.bind(this);
        this.showAlert = this.showAlert.bind(this);
    }

    setStatePromise(newState) {
        return new Promise((resolve) => {
            this.setState(newState, () => {
                resolve();
            });
        });
    }

    updatePseudo = event => {this.setState({pseudo: event.target.value});};

    updateMail = event => {this.setState({mail: event.target.value});};

    updatePassword = event => {this.setState({password: event.target.value});};

    updateConfirmPassword = event => {this.setState({confirm_password: event.target.value});};

    updateSkype = event => {this.setState({skype: event.target.value});};

    showAlert = (message, type) => {this.alert.show(message, {type: type})};

    register = event => {
        event.preventDefault();
        if(this.state.pseudo && this.state.mail && this.state.password && this.state.confirm_password && this.state.skype){
            if (this.state.password !== this.state.confirm_password){
                document.getElementById("registerPassword").classList.add("password-over-red");
                document.getElementById("registerConfirmPassword").classList.add("password-over-red");
                this.showAlert("Les mots de passe rentrés ne correspondent pas", "error");
            } else {
                const ctx = this;

                const user = this.state.user;
                user.pseudo = this.state.pseudo;
                user.mail = this.state.mail;
                user.password = this.state.password;
                user.skype = this.state.skype;
                user.active = true;

                this.setStatePromise({user : user}).then(() => {
                    $.ajax({
                        crossDomain: true,
                        type: "POST",
                        url: API_URL + "/user",
                        data: JSON.stringify(this.state.user),
                        success: function (response) {
                            if (response.error){
                                ctx.showAlert(response.error, "error");
                                console.log(response.stackTrace);
                            } else if (response.pseudo){
                                ctx.showAlert("Votre compte a été créé avec succès. Redirection en cours.", "success");
                                ctx.setStatePromise({user: response}).then(() => {
                                    window.location.href = "/connexion";
                                    window.location.replace("/connexion");
                                });
                            } else {
                                ctx.showAlert("Une erreur technique est apparue. Merci de recommencer dans quelques instants.", "error");
                            }
                        },
                        error: function (error) {
                            if (error.error) {
                                ctx.showAlert(error, "error");
                            } else {
                                ctx.showAlert("Erreur: il est possible que ce pseudo, cette adresse mail ou ce compte skype soit déjà utilisé.", "error");
                            }
                        }
                    });
                });
            }
        }
    };

    render() {
        return (
            <div className="container">
                <AlertContainer ref={a => this.alert = a} {...this.alertOptions} />
                <div className="col-md-3"/>
                <div className="col-md-6 login-margin-top">
                    <h1 className="page-title">Inscription</h1>
                    <form className="form-horizontal" onSubmit={e => this.register(e)}>
                        <div className="form-group no-margin">
                            <label className="control-label form-label">Pseudo</label>
                            <div className="input-group">
                                <span className="input-group-addon"><i className="fa fa-user-circle"
                                                                       aria-hidden="true"/></span>
                                <input type="text" className="form-control" id="registerPseudo" name="registerPseudo"
                                       ref={input => this.registerPseudo = input} placeholder="Mon pseudo"
                                       onChange={this.updatePseudo} required
                                />
                            </div>
                        </div>
                        <div className="form-group no-margin">
                            <label className="control-label form-label">Adresse email</label>
                            <div className="input-group">
                                <span className="input-group-addon"><i className="fa fa-envelope"
                                                                       aria-hidden="true"/></span>
                                <input type="email" className="form-control" id="registerMail" name="registerMail"
                                       ref={input => this.registerMail = input} placeholder="Mon adresse email"
                                       onChange={this.updateMail} required
                                />
                            </div>
                        </div>
                        <div className="form-group no-margin">
                            <label className="control-label form-label">Mot de passe</label>
                            <div className="input-group">
                                <span className="input-group-addon"><i className="fa fa-unlock"
                                                                       aria-hidden="true"/></span>
                                <input type="password" className="form-control" id="registerPassword" name="registerPassword"
                                       ref={input => this.registerPassword = input} placeholder="Mon mot de passe"
                                       onChange={this.updatePassword} required
                                />
                            </div>
                        </div>
                        <div className="form-group no-margin">
                            <label className="control-label form-label">Confirmer le mot de passe</label>
                            <div className="input-group">
                                <span className="input-group-addon"><i className="fa fa-unlock"
                                                                       aria-hidden="true"/></span>
                                <input type="password" className="form-control" id="registerConfirmPassword" name="registerConfirmPassword"
                                       ref={input => this.registerConfirmPassword = input} placeholder="Mon mot de passe"
                                       onChange={this.updateConfirmPassword} required
                                />
                            </div>
                        </div>
                        <div className="form-group no-margin">
                            <label className="control-label form-label">Pseudo skype</label>
                            <div className="input-group">
                                <span className="input-group-addon"><i className="fa fa-skype"
                                                                       aria-hidden="true"/></span>
                                <input type="text" className="form-control" id="registerSkype" name="registerSkype"
                                       ref={input => this.registerSkype = input} placeholder="Mon compte skype"
                                       onChange={this.updateSkype} required
                                />
                            </div>
                        </div>
                        <button type="submit" className="btn btn-primary btn-login"
                                data-toggle="tooltip" data-placement="left" title=""
                                data-original-title="Tooltip on left">
                            Valider
                        </button>
                    </form>
                    <Link to="/connexion">
                        <button type="submit" className="btn btn-default center-block btn-go-to-login" data-toggle="tooltip"
                                data-placement="left" title="" data-original-title="Tooltip on left">
                            J'ai déjà un compte
                        </button>
                    </Link>
                </div>
                <div className="col-md-3"/>
            </div>
        );
    }
}

export default Register;