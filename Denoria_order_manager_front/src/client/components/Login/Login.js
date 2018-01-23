import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import AlertContainer from 'react-alert';
import {API_URL, ajaxSetup, alertOptions} from "../../../configuration/Configuration";
import $ from 'jquery';
import '../../assets/css/Login.css';

class Login extends Component {

    state = {
        pseudo: "",
        password: "",
        user: {
            pseudo: "",
            mail: "",
            password: "",
            avatar_link: "",
            token: "",
            skype: "",
            active: false
        }
    };

    constructor(props) {
        super(props);
        this.ctx = ajaxSetup();
        this.alertOptions = alertOptions;
        this.setStatePromise = this.setStatePromise.bind(this);
        this.updatePassword = this.updatePassword.bind(this);
        this.updatePseudo = this.updatePseudo.bind(this);
        this.requestLogin = this.requestLogin.bind(this);
        this.showAlert = this.showAlert.bind(this);
    }

    /**
     * Set a state with a promise
     * @param newState the sate to set
     * @returns {Promise} .then
     */
    setStatePromise(newState) {
        return new Promise((resolve) => {
            this.setState(newState, () => {
                resolve();
            });
        });
    }

    updatePseudo = event => {
        this.setState({pseudo: event.target.value});
    };

    updatePassword = event => {
        this.setState({password: event.target.value});
    };

    requestLogin = event => {
        event.preventDefault();

        const ctx = this;
        const user = this.state.user;
        user.pseudo = this.state.pseudo;
        user.password = this.state.password;

        if(user.pseudo.length <= 0 || user.password.length <= 0){
            this.showAlert("Tous les champs doivent être remplis !", "error");
        } else {
            this.setStatePromise({user: user}).then(() => {
                $.ajax({
                    type: "POST",
                    url: API_URL + "/user/request-login",
                    data: JSON.stringify(this.state.user),
                    success: function (response) {
                        if (response.error){
                            ctx.showAlert(response.error, "error");
                            console.log(response.stackTrace);
                        } else if (response.pseudo){
                            if(response.active === true){
                                ctx.showAlert("Connexion effectuée, redirection en cours.", "success");
                                localStorage.setItem("user-pseudo", response.pseudo);
                                localStorage.setItem("user-mail", response.mail);
                                localStorage.setItem("user-avatar", response.avatar_link);
                                localStorage.setItem("session-token", response.token);
                                localStorage.setItem("user-skype", response.skype);
                                localStorage.setItem("z-user", true);

                                ctx.setStatePromise({user: response}).then(() => {
                                    window.location.href = "/";
                                    window.location.replace("/");
                                });
                            } else {
                                ctx.showAlert("Ce compte est désactivé. Vous ne pouvez pas vous connecter avec !", "error");
                            }

                        } else {
                            ctx.showAlert("Une erreur technique est apparue. Merci de recommencer dans quelques instants.", "error");
                        }
                    },
                    error: function () {
                        ctx.showAlert("Une erreur technique est apparue pendant la requête au serveur.", "error");
                    }
                });
            });
        }
    };

    showAlert = (message, type) => {
        this.alert.show(message, {
            type: type
        })
    };

    render() {
        return (
            <div className="container">
                <AlertContainer ref={a => this.alert = a} {...this.alertOptions} />
                <div className="col-md-3"/>
                <div className="col-md-6 login-margin-top">
                    <h1 className="page-title">Connexion</h1>
                    <form className="form-horizontal" onSubmit={e => this.requestLogin(e)}>
                        <div className="form-group no-margin">
                            <label className="control-label form-label">Pseudo</label>
                            <div className="input-group">
                                <span className="input-group-addon"><i className="fa fa-user-circle"
                                                                       aria-hidden="true"/></span>
                                <input type="text" className="form-control" id="loginPseudo" name="loginPseudo"
                                       ref={input => this.loginPseudo = input} placeholder="Mon pseudo"
                                       onChange={this.updatePseudo} required
                                />
                            </div>
                        </div>
                        <div className="form-group no-margin">
                            <label className="control-label form-label">Mot de passe</label>
                            <div className="input-group">
                                <span className="input-group-addon"><i className="fa fa-unlock"
                                                                       aria-hidden="true"/></span>
                                <input type="password" className="form-control" id="loginPassword" name="loginPassword"
                                       ref={input => this.loginPassword = input} placeholder="Mon mot de passe"
                                       onChange={this.updatePassword} required
                                />
                            </div>
                        </div>
                        <button type="submit" className="btn btn-primary btn-login"
                                data-toggle="tooltip" data-placement="left" title=""
                                data-original-title="Tooltip on left">
                            Connexion
                        </button>
                    </form>
                    <Link to="/inscription">
                        <button type="submit" className="btn btn-default btn-login" data-toggle="tooltip"
                                data-placement="left" title="" data-original-title="Tooltip on left">
                            Créer un compte
                        </button>
                    </Link>
                </div>
                <div className="col-md-3"/>
            </div>
        );
    }
}

export default Login;