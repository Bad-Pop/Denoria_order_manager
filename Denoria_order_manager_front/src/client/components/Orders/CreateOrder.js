import React, {Component} from 'react';
import AlertContainer from 'react-alert';
import {API_URL, ajaxSetup, alertOptions, takingOrders, takingBuildOrders, takingDevOrders} from "../../../configuration/Configuration";
import {Alert} from 'react-bootstrap';
import ReactQuill from 'react-quill';
import AjaxLoading from '../../views/AjaxLoading/AjaxLoading';
import $ from 'jquery';
import {Link} from 'react-router-dom';
import {Button} from 'react-bootstrap';

import 'react-quill/dist/quill.snow.css';
import '../../assets/css/CreateNewOrder.css';

class CreateOrder extends Component {

    state = {
        title: "",
        description: "",
        specificationsLink: "",
        budget: 0,
        orderDate: "",
        orderType: "",
        order:{
            id: "",
            title: "",
            description: "",
            specificationsLink: "",
            budget: 0,
            price: 0,
            orderStatus: "Ouverte",
            paymentStatus: "En attente",
            orderDate: "",
            orderType: "",
            user: {
                pseudo: "",
                mail: "",
                password: "",
                avatar_link: "",
                token: "",
                skype: "",
                active: true
            },
        },
        user: {
            pseudo: "",
            mail: "",
            password: "",
            avatar_link: "",
            token: "",
            skype: "",
            active: true
        },
        showLoading: false,
        showSuccessfullyCreatedOrder: false,
    };

    constructor(props) {
        super(props);
        this.cfg = ajaxSetup();
        this.alertOptions = alertOptions;
        this.setStatePromise = this.setStatePromise.bind(this);
        this.showAlert = this.showAlert.bind(this);
        this.createNewOrder = this.createNewOrder.bind(this);
        this.updateTitle = this.updateTitle.bind(this);
        this.updateDescription = this.updateDescription.bind(this);
        this.updateSpec = this.updateSpec.bind(this);
        this.updateOrderType = this.updateOrderType.bind(this);
    }

    componentDidMount(){
        let user = this.state.user;
        user.pseudo = localStorage.getItem("user-pseudo");
        user.mail = localStorage.getItem("user-mail");
        user.token = localStorage.getItem("session-token");
        user.skype = localStorage.getItem("user-skype");
        this.setState({user: user});
    }

    setStatePromise(newState){return new Promise((resolve) => {this.setState(newState, () => {resolve();});});}

    showAlert = (message, type) => {this.alert.show(message, {type: type})};

    updateTitle = event =>{
        if(event.target.value.length <= 64){
            this.setState({title: event.target.value});
        } else {
            this.showAlert("Nombre de caractères limite atteint.", "info");
        }
    };

    updateSpec = event =>{this.setState({specificationsLink: event.target.value});};

    updateOrderType = event =>{this.setState({orderType: event.target.value});};

    updateDescription(value){this.setState({description: value});}

    updateBudget = event =>{

        const regEx = /^\d+$/;
        // eslint-disable-next-line
        if(regEx.test(event.target.value) && event.target.value > 0 && parseInt(event.target.value)){
            // eslint-disable-next-line
            this.setState({budget: parseInt(event.target.value)});
            document.getElementById("orderBudget").classList.remove("input-over-red");
        } else {
            this.showAlert("Merci de saisir un budget valide", "error");
            document.getElementById("orderBudget").classList.add("input-over-red");
        }
    };

    createNewOrder = event => {
        event.preventDefault();
        this.setState({showLoading: true});
        const order = this.state.order;
        const ctx = this;
        const currentDate = new Date();

        order.user = this.state.user;
        order.title = this.state.title;
        order.description = this.state.description;
        order.specificationsLink = this.state.specificationsLink;
        order.budget = this.state.budget;
        order.orderDate = "" + currentDate.getDate() + "-" + currentDate.getMonth()+1 + "-" + currentDate.getFullYear();
        order.orderType = this.state.orderType;

        this.setStatePromise({order: order}).then(() => {
            $.ajax({
                type: "POST",
                url: API_URL + "/order",
                data: JSON.stringify(this.state.order),
                headers: {
                    "z-user": true,
                    "z-token": this.state.user.token,
                    "z-pseudo": this.state.user.pseudo
                },
                success: function (response) {
                    if (response.error){
                        ctx.showAlert(response.error, "error");
                        console.log(response.stackTrace);
                    } else if (response.id) {
                        ctx.showAlert("Commande passée avec succès. Vous allez être redirigé vers votre commande.", "success");
                        ctx.setState({showSuccessfullyCreatedOrder: true});
                        setTimeout(function(){
                            window.location.href = "/profil/" + ctx.state.user.pseudo + "/" + response.id;
                            window.location.replace("/profil/" + ctx.state.user.pseudo + "/" + response.id);
                        }, 3000);
                    }
                    ctx.setState({showLoading: false});
                },
                error: function () {
                    ctx.showAlert("Une erreur technique est apparue pendant la requête au serveur.", "error");
                    ctx.setState({showLoading: false});
                }
            });
        });
    };

    render() {
        return (
            <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <AlertContainer ref={a => this.alert = a} {...this.alertOptions} />
                {
                    takingOrders
                    ?
                        <div>
                            {this.state.showLoading ? <AjaxLoading/> : null}
                            {
                                this.state.showSuccessfullyCreatedOrder
                                    ?
                                    <div>
                                        <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                            <Alert bsStyle="success">
                                                <p>Commande passée avec succès. Vous allez être redirigé vers votre commande.</p>
                                            </Alert>
                                        </div>
                                    </div>
                                    :
                                    <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                        <Alert bsStyle="danger">
                                            <p>Attention: toute commande ne contenant pas un cahier des charges complet et détaillé de ce que vous souhaitez se retrouvera refusée sans préavis.</p>
                                        </Alert>
                                        <h1>Passer une commande</h1>
                                        <div className="page-hr"/>
                                        <p className="text-danger">Tous les champs sont obligatoires.</p>
                                        <form onSubmit={e=>this.createNewOrder(e)} className="form-group">

                                            <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <label className="control-label form-label">Titre de votre commande</label>
                                                <div className="input-group">
                                                    <span className="input-group-addon"><i className="fa fa-check" aria-hidden="true"/></span>
                                                    <input maxLength="64" type="text" className="form-control"
                                                           id="orderTitle" name="orderTitle"
                                                           ref={input => this.orderTitle = input}
                                                           placeholder="Titre de ma commande (64 caractères maximum)"
                                                           onChange={this.updateTitle} required
                                                    />
                                                </div>
                                            </div>
                                            <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <label className="form-group form-label">Description de votre commande</label>
                                                <ReactQuill value={this.state.description} onChange={this.updateDescription} />
                                            </div>
                                            <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <label className="control-label form-label">Lien du cahier des charges complet</label>
                                                <div className="input-group">
                                                    <span className="input-group-addon"><i className="fa fa-file-o" aria-hidden="true"/></span>
                                                    <input maxLength="255" type="text" className="form-control"
                                                           id="orderSpec" name="orderSpec"
                                                           ref={input => this.orderSpec = input}
                                                           placeholder="http://lien-vers-mon-cahier-des-charges.fr"
                                                           onChange={this.updateSpec} required
                                                    />
                                                </div>
                                            </div>

                                            <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <label className="control-label form-label">Votre budget (en € sans virgule ni point)</label>
                                                <div className="input-group">
                                                    <span className="input-group-addon"><i className="fa fa-money" aria-hidden="true"/></span>
                                                    <input type="number" min="0" className="form-control"
                                                           id="orderBudget" name="orderBudget"
                                                           ref={input => this.orderBudget = input}
                                                           placeholder="20"
                                                           onChange={this.updateBudget} required
                                                    />
                                                </div>
                                            </div>
                                            <div className="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <label className="control-label form-label">Type de commande</label>
                                                <div className="input-group">
                                                    <span className="input-group-addon"><i className="fa fa-file-o" aria-hidden="true"/></span>
                                                    <select className="form-control"
                                                            id="orderType" name="orderType"
                                                            onChange={this.updateOrderType}
                                                            ref={select => this.orderType = select}
                                                    >
                                                        <option value="Veuillez selectionner un type de commande ...">Veuillez selectionner un type de commande ...</option>
                                                        {
                                                            takingBuildOrders
                                                            ?
                                                                <option value="BUILD">BUILD</option>
                                                            :
                                                                null
                                                        }
                                                        {
                                                            takingDevOrders
                                                            ?
                                                                <option value="DEVELOPPEMENT">DEVELOPPEMENT</option>
                                                            : null
                                                        }
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
                    :
                        <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                            <h1 className="text-danger">Notre système de commande est temporairement désactivé</h1>
                            <p>En raison d'un trop grand nombre de demande, nous refusons toute nouvelle commande afin de pouvoir nous concentrer sur nos projets internes ainsi que sur les commandes en cours.</p>
                            <p>Nous souhaitons nous excuser pour la gêne occasionnée et restons à votre entière disposition sur nos réseaux sociaux ainsi que sur nos différents moyens de contact.</p>
                            <p>Nous vous avertirons dès que nous serons en mesure de reprendre des commandes.</p>
                            <Link to="/"><Button bsStyle="danger">Retourner sur mon tableau de bord</Button></Link>
                        </div>
                }
            </div>
        );
    }
}

export default CreateOrder;