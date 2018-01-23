import React, {Component} from 'react';
import $ from 'jquery';
import {ajaxSetup, alertOptions, API_URL} from "../../../configuration/Configuration";
import AlertContainer from 'react-alert';
import ReactQuill from 'react-quill';
import {Alert, Button} from 'react-bootstrap';
import AjaxLoading from '../../views/AjaxLoading/AjaxLoading';
import {Link} from 'react-router-dom';

import 'react-quill/dist/quill.snow.css';
import '../../assets/css/CreateCandidature.css';

class CreateCandidature extends Component{

    state = {
        showLoading: false,
        alreadyCandidate: false,
        name: "",
        age: 0,
        personnalDescription: "",
        howLongPlayMinecraft: "",
        hoursPerWeek: "",
        why: "",
        desiredRole: "BUILDER",
        candidature: {
            name: "",
            age: 0,
            personnalDescription: "",
            howLongPlayMinecraft: "",
            hoursPerWeek: "",
            why: "",
            desiredRole: "",
            status: "EN ATTENTE",
            user: {
                pseudo: localStorage.getItem("user-pseudo"),
                token: localStorage.getItem("session-token"),
            }
        },
    };

    constructor(props){
        super(props);
        this.cfg = ajaxSetup();
        this.alertOptions = alertOptions;

        this.componentDidMount = this.componentDidMount.bind(this);
        this.updateName = this.updateName.bind(this);
        this.updateDesiredRole = this.updateDesiredRole.bind(this);
        this.updateHowLongPlayMinecraft = this.updateHowLongPlayMinecraft.bind(this);
        this.updateHoursPerWeek = this.updateHoursPerWeek.bind(this);
        this.updatePersonnaleDescription = this.updatePersonnaleDescription.bind(this);
        this.updateWhy = this.updateWhy.bind(this);
        this.updateAge = this.updateAge.bind(this);
        this.createNewCandidature = this.createNewCandidature.bind(this);
        this.showAlert = this.showAlert.bind(this);
    }

    componentDidMount(){

        this.setState({showLoading: true});
        const ctx = this;

        $.ajax({
            type: "GET",
            url: API_URL + "/candidature/user/" + localStorage.getItem("user-pseudo"),
            headers: {
                "z-user": true,
                "z-token": localStorage.getItem("session-token"),
                "z-pseudo": localStorage.getItem("user-pseudo")
            },
            success: function (response) {
                if (response.error){
                    if(response.error === "Cet utilisateur n'a pas fait de candidature"){
                        ctx.setState({alreadyCandidate: false});
                    } else {
                        console.log(response.stackTrace);
                        ctx.setState({alreadyCandidate: false});
                    }
                } else if (response.id) {
                    ctx.setState({alreadyCandidate: true});
                }
                ctx.setState({showLoading: false});
            },
            error: function () {
                ctx.setState({showLoading: false});
            }
        });
    }

    updateName = event =>{
        this.setState({name: event.target.value});
    };

    updateDesiredRole = event =>{
        this.setState({desiredRole: event.target.value});
    };

    updateHowLongPlayMinecraft = event =>{
        this.setState({howLongPlayMinecraft: event.target.value});
    };

    updateHoursPerWeek = event =>{
        this.setState({hoursPerWeek: event.target.value});
    };

    updatePersonnaleDescription(value){
        this.setState({personnalDescription: value});
    }

    updateWhy(value){
        this.setState({why: value});
    }

    updateAge = event =>{
        const regEx = /^\d+$/;
        // eslint-disable-next-line
        if(regEx.test(event.target.value) && event.target.value > 0 && parseInt(event.target.value)){
            // eslint-disable-next-line
            this.setState({age: parseInt(event.target.value)});
            document.getElementById("candAge").classList.remove("input-over-red");
        } else {
            this.showAlert("Merci de saisir un âge valide", "error");
            document.getElementById("candAge").classList.add("input-over-red");
        }
    };

    createNewCandidature = event => {
        event.preventDefault();

        this.setState({showLoading: true});

        const ctx = this;
        if(this.state.name && this.state.age && this.state.personnalDescription &&
            this.state.howLongPlayMinecraft && this.state.hoursPerWeek &&
            this.state.why && this.state.desiredRole && this.state.age > 0){

            const candidature = this.state.candidature;
            candidature.name = this.state.name;
            candidature.age = this.state.age;
            candidature.personnalDescription = this.state.personnalDescription;
            candidature.howLongPlayMinecraft = this.state.howLongPlayMinecraft;
            candidature.hoursPerWeek = this.state.hoursPerWeek;
            candidature.why = this.state.why;
            candidature.desiredRole = this.state.desiredRole;

            $.ajax({
                type: "POST",
                url: API_URL + "/candidature/",
                headers: {
                    "z-user": true,
                    "z-token": localStorage.getItem("session-token"),
                    "z-pseudo": localStorage.getItem("user-pseudo")
                },
                data: JSON.stringify(candidature),
                success: function (response) {
                    if (response.error){
                        ctx.showAlert(response.error, "error");
                        console.log(response.stackTrace);
                    } else if (response.id) {
                        ctx.showAlert("Votre candidature a bien été envoyée. Redirection vers votre tableau de board en cours !", "success");
                        window.location.href = "/";
                        window.location.replace("/");
                    }
                    ctx.setState({showLoading: false});
                },
                error: function () {
                    ctx.showAlert("Une erreur technique est apparue pendant la requête au serveur.", "error");
                    ctx.setState({showLoading: false});
                }
            });
            this.setState({showLoading: false});
        } else {
            this.showAlert("Merci de bien remplir le formulaire correctement !", "error");
        }
    };

    showAlert = (message, type) => {
        this.alert.show(message, {type: type})
    };

    render(){
        return(
            <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <AlertContainer ref={a => this.alert = a} {...this.alertOptions} />
                {
                    this.state.showLoading
                    ?
                        <AjaxLoading/>
                    :
                        this.state.alreadyCandidate
                            ?
                            <div>
                                <Alert bsStyle="danger" onDismiss={this.betaAlertDismiss}>
                                    <h4>Vous ne pouvez pas postuler une nouvelle fois !</h4>
                                    <p>
                                        Vous avez déjà postulé. Vous ne pouvez donc pas postuler de nouveau via notre interface.
                                        Les doubles candidatures se font au cas par cas avec notre gestionnaire de candidatures.
                                    </p>
                                </Alert>
                                <Link to="/"><Button bsStyle="warning">Retourner au tableau de bord</Button></Link>
                            </div>
                            :
                            <div>
                                <h1 className="text-uppercase">Postuler dans l'une de nos équipes</h1>
                                <div className="page-hr"/>
                                <p className="text-danger">Tous les champs sont obligatoires</p>
                                <form onSubmit={e => this.createNewCandidature(e)} className="form-group">
                                    <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label className="control-label form-label">Votre prénom</label>
                                        <div className="input-group">
                                            <span className="input-group-addon"><i className="fa fa-user-circle" aria-hidden="true"/></span>
                                            <input maxLength="64" type="text" className="form-control"
                                                   id="candName" name="candName"
                                                   ref={input => this.candName = input}
                                                   placeholder="Votre prénom"
                                                   onChange={this.updateName} required
                                            />
                                        </div>
                                    </div>
                                    <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label className="control-label form-label">Votre âge</label>
                                        <div className="input-group">
                                            <span className="input-group-addon"><i className="fa fa-user-circle" aria-hidden="true"/></span>
                                            <input maxLength="64" type="number" className="form-control"
                                                   id="candAge" name="candAge"
                                                   ref={input => this.candAge = input}
                                                   placeholder="Votre age"
                                                   onChange={this.updateAge} required
                                            />
                                        </div>
                                    </div>
                                    <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label className="form-group form-label">Description personnelle</label>
                                        <ReactQuill value={this.state.personnalDescription} onChange={this.updatePersonnaleDescription} />
                                    </div>
                                    <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label className="control-label form-label">Depuis combien de temps jouez vous à minecraft ?</label>
                                        <div className="input-group">
                                            <span className="input-group-addon"><i className="fa fa-clock-o" aria-hidden="true"/></span>
                                            <input maxLength="64" type="text" className="form-control"
                                                   id="candHowLongPlayMinecraft" name="candHowLongPlayMinecraft"
                                                   ref={input => this.candHowLongPlayMinecraft = input}
                                                   placeholder="Exemple: 5 ans"
                                                   onChange={this.updateHowLongPlayMinecraft} required
                                            />
                                        </div>
                                    </div>
                                    <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label className="control-label form-label">Combien d'heures êtes-vous disponible par semaine ?</label>
                                        <div className="input-group">
                                            <span className="input-group-addon"><i className="fa fa-clock-o" aria-hidden="true"/></span>
                                            <input maxLength="64" type="text" className="form-control"
                                                   id="candHoursPerWeek" name="candHoursPerWeek"
                                                   ref={input => this.candHoursPerWeek = input}
                                                   placeholder="Exemple: 10 heures"
                                                   onChange={this.updateHoursPerWeek} required
                                            />
                                        </div>
                                    </div>
                                    <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label className="form-group form-label">Pourquoi postulez-vous chez nous ?</label>
                                        <ReactQuill value={this.state.why} onChange={this.updateWhy} />
                                    </div>
                                    <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <label className="control-label form-label">Pour quel rôle postulez-vous ?</label>
                                        <div className="input-group">
                                            <span className="input-group-addon"><i className="fa fa-rocket" aria-hidden="true"/></span>
                                            <select className="form-control"
                                                    id="candDesiredRole" name="candDesiredRole"
                                                    onChange={this.updateDesiredRole}
                                                    ref={select => this.candDesiredRole = select}
                                            >
                                                <option value="BUILDER">BUILDER</option>
                                                <option value="TERRAFORMEUR">TERRAFORMEUR</option>
                                                <option value="ORGANIC MAKER">ORGANIC MAKER</option>
                                                <option value="VIDEO MAKER">VIDEO MAKER</option>
                                                <option value="GRAPHISTE">GRAPHISTE</option>
                                                <option value="COMMUNITY MANAGER">COMMUNITY MANAGER</option>
                                                <option value="DEVELOPPEUR">DEVELOPPEUR</option>
                                                <option value="ADMINISTRATEUR SYSTEME">ADMINISTRATEUR SYSTEME</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <button type="submit" className="btn btn-info">Valider</button>
                                    </div>
                                </form>
                            </div>
                }
            </div>
        )
    }
}
export default CreateCandidature;