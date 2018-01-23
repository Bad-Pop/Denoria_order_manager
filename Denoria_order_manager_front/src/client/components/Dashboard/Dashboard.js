import React, {Component} from 'react';
import { Alert, Button } from 'react-bootstrap';
import {Link} from 'react-router-dom';
import $ from 'jquery';
import { ajaxSetup, API_URL, alertOptions } from "../../../configuration/Configuration";
import AlertContainer from 'react-alert';
import DashboardOrderList from './Order/DashboardOrderList';
import AjaxLoading from "../../views/AjaxLoading/AjaxLoading";
import DashboardCandidature from './Candidature/DashboardCandidature';

import '../../assets/css/Dashboard.css';

class Dashboard extends Component {

    state = {
        betaAlertVisible: true,
        orders: {},
        candidature: {},
        user: {
            pseudo: "",
            mail: "",
            password: "",
            avatar_link: "",
            token: "",
            skype: "",
            active: true
        },
        showOrderList: false,
        showCandidature: false,
        showLoading: false,
    };

    constructor(props){
        super(props);
        this.cfg = ajaxSetup();
        this.alertOptions = alertOptions;
        this.betaAlertDismiss = this.betaAlertDismiss.bind(this);
        this.showAlert = this.showAlert.bind(this);
        this.showOrderList = this.showOrderList.bind(this);
        this.setStatePromise = this.setStatePromise.bind(this);
        this.showCandidature = this.showCandidature.bind(this);
    }

    componentDidMount(){
        let user = this.state.user;
        user.pseudo = localStorage.getItem("user-pseudo");
        user.mail = localStorage.getItem("user-mail");
        user.token = localStorage.getItem("session-token");
        user.skype = localStorage.getItem("user-skype");
        this.setState({user: user});
    }

    setStatePromise(newState) {
        return new Promise((resolve) => {
            this.setState(newState, () => {
                resolve();
            });
        });
    }

    betaAlertDismiss() {
        this.setState({betaAlertVisible: false});
    }

    showAlert = (message, type) => {
        this.alert.show(message, {
            type: type
        })
    };

    showCandidature(){
        const ctx = this;

        if(this.state.showCandidature === false){
            this.setState({showOrderList: false});
            if(this.state.candidature.name){
                this.setState({showCandidature: true});
            } else {
                this.setState({showLoading: true});
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
                            ctx.showAlert(response.error, "error");
                            console.log(response.stackTrace);
                            ctx.setState({showCandidature: true})
                        } else if (response.id) {
                            ctx.setStatePromise({candidature: response}).then(() => {
                                ctx.setState({showCandidature: true});
                                ctx.showAlert("Candidature affichée avec succès", "success");
                            });
                        }
                        ctx.setState({showLoading: false});
                    },
                    error: function () {
                        ctx.setState({showLoading: false});
                    }
                });
            }
        } else {
            this.setState({showCandidature: false});
        }
    }

    showOrderList(){
        const ctx = this;

        if(this.state.showOrderList === false){
            this.setState({showCandidature: false});
            if(this.state.orders.length >= 1){
                this.setState({showOrderList: true});
            } else {
                this.setState({showLoading: true});
                $.ajax({
                    type: "POST",
                    url: API_URL + "/order/user",
                    data: JSON.stringify(this.state.user),
                    headers: {
                        "z-user": true,
                        "z-token": this.state.user.token,
                        "z-pseudo": this.state.user.pseudo
                    },
                    success: function (response) {
                        if (response.error){
                            ctx.showAlert(response.error, "error");
                            console.log(response.stackTrace);
                            ctx.setState({showOrderList: true});
                        } else if (response) {
                            ctx.setStatePromise({orders: response}).then(() => {
                                ctx.setState({showOrderList: true});
                            });
                            ctx.showAlert("Vos commandes sont maintenant affichées !", "success");
                        } else {
                            ctx.setState({showOrderList: true});
                            ctx.showAlert("Vous n'avez passé aucune commande sur notre outil.", "info");
                        }
                        ctx.setState({showLoading: false});
                    },
                    error: function () {
                        ctx.showAlert("Une erreur technique est apparue pendant la requête au serveur.", "error");
                        ctx.setState({showLoading: false});
                    }
                });
            }
        } else {
            this.setState({showOrderList: false});
        }
    }

    render () {
        const userPseudo = localStorage.getItem("user-pseudo");
        const avatarUrl = "http://cravatar.eu/helmavatar/" + userPseudo + "/52.png";
        return (
            <div>
                <AlertContainer ref={a => this.alert = a} {...this.alertOptions} />
                {this.state.showLoading ? <AjaxLoading/> : null}
                {
                    this.state.betaAlertVisible
                    ?
                        <Alert bsStyle="danger" onDismiss={this.betaAlertDismiss}>
                            <h4>Cette interface est en version bêta</h4>
                            <p>Il est possible que vous rencontriez des erreurs. Si vous trouvez un bug pensez à nous le signaler, nous le corrigerons dans les plus brefs délais.</p>
                            <p>De prochaines améliorations, et fonctionnalités sont également prévues. Nous vous avertirons dès qu'elles serront disponibles !</p>
                        </Alert>
                    :
                        null
                }
                <h1 className="text-uppercase text-info"><img src={avatarUrl} alt={userPseudo} className="img-circle"/> Mon tableau de bord</h1>
                <div className="page-hr"/>
                <div className="dashboard-container">
                    <div className="col-xs12 col-sm-12 col-md-12 col-lg-12">
                        <p>Bienvenue sur votre interface personnelle. Grâce à notre nouvel outil, vous pourrez passer des commandes et, ou, postuler dans l'une de nos équipes.</p>
                    </div>
                    <div className="col-xs-12 col-sm-12 col-md-3 col-lg-3 no-margin">
                        <ul className="fast-actions">
                            <li><h3>Actions rapides</h3></li>
                            <li><Button bsStyle="danger fast-action-button" onClick={this.showOrderList}>Mes commandes</Button></li>
                            <li><Link to="/nouvelle_commande"><Button bsStyle="warning fast-action-button">Nouvelle commande</Button></Link></li>
                            <li><Button bsStyle="success fast-action-button" onClick={this.showCandidature}>Ma candidature</Button></li>
                            <li><Link to="/nouvelle_candidature"><Button bsStyle="info fast-action-button">Nouvelle candidature</Button></Link></li>
                        </ul>
                        {/*TODO ADD ANNOUNCE*/}
                    </div>
                    {
                        this.state.showCandidature
                            ?
                                <DashboardCandidature candidature={this.state.candidature}/>
                            :
                                null
                    }
                    {
                        this.state.showOrderList
                        ?
                            <DashboardOrderList orders={this.state.orders}/>
                        :
                            null
                    }
                </div>
            </div>
        )
    }
}
export default Dashboard