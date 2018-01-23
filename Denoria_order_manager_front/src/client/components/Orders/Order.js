import React, {Component} from 'react';
import AlertContainer from 'react-alert';
import {API_URL, ajaxSetup, alertOptions} from "../../../configuration/Configuration";
import $ from 'jquery';
import AjaxLoading from '../../views/AjaxLoading/AjaxLoading';
import {Label, Alert, Popover, OverlayTrigger} from 'react-bootstrap';

import '../../assets/css/Order.css';

class Order extends Component {

    state = {
        user: {
            pseudo: "",
            mail: "",
            password: "",
            avatar_link: "",
            token: "",
            skype: "",
            active: true
        },
        order: {
            id: 0,
            title: "",
            description: "",
            specificationsLink: "",
            budget: 0,
            price: 0,
            orderDate: "",
            orderStatus: "",
            paymentStatus: "",
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
        showLoading: false,
    };

    constructor(props) {
        super(props);
        this.cfg = ajaxSetup();
        this.alertOptions = alertOptions;
        this.showUserOrder = this.showUserOrder.bind(this);
        this.showAlert = this.showAlert.bind(this);
        this.setStatePromise = this.setStatePromise.bind(this);
    }

    componentDidMount() {
        let user = this.state.user;

        user.pseudo = localStorage.getItem("user-pseudo");
        user.mail = localStorage.getItem("user-mail");
        user.token = localStorage.getItem("session-token");
        user.skype = localStorage.getItem("user-skype");

        this.setStatePromise({user: user}).then(() => {
            if (this.props.match.params.id && this.props.match.params.userPseudo) {
                this.showUserOrder(this.props.match.params.id, this.props.match.params.userPseudo);
            }
        });
    }

    setStatePromise(newState) {
        return new Promise((resolve) => {
            this.setState(newState, () => {
                resolve();
            });
        });
    }

    showUserOrder(id, pseudo) {
        const ctx = this;

        this.setState({showLoading: true});
        $.ajax({
            type: "GET",
            url: API_URL + "/order/id/" + ctx.props.match.params.id,
            headers: {
                "z-user": true,
                "z-token": this.state.user.token,
                "z-pseudo": this.state.user.pseudo
            },
            success: function (response) {
                if (response.error) {
                    ctx.showAlert(response.error, "error");
                    ctx.setState({showLoading: false});
                } else if (response.id) {
                    if (response.user.pseudo === ctx.state.user.pseudo) {
                        ctx.setStatePromise({order: response}).then(() => {
                            ctx.setState({showLoading: false});
                            ctx.showAlert("Votre commande est maintenant affichée.", "success")
                        });
                    } else {
                        ctx.showAlert("Imoossible d'afficher cette commande. Redirection en cours !", "error");
                        setTimeout(function(){
                            window.location.href = "/";
                            window.location.replace("/");
                        }, 3000);
                    }
                }
            },
            error: function () {
                ctx.showAlert("Une erreur technique est apparue pendant la requête au serveur. Redirection en cours !", "error");
                setTimeout(function(){
                    window.location.href = "/";
                    window.location.replace("/");
                    }, 3000);

            }
        });

    };

    showAlert = (message, type) => {
        this.alert.show(message, {
            type: type
        })
    };

    render() {
        const popoverPaymentSection = (
            <Popover id="popover-trigger-hover-focus" title="ATTENTION">
                <p className="text-danger">
                    Il est conseillé de ne payer votre commande qu'une fois cette dernière terminée et que vous l'avez
                    validée avec nous. Aucun remboursement possible.
                </p>
            </Popover>
        );
        return (
            <div>
                <AlertContainer ref={a => this.alert = a} {...this.alertOptions} />
                {this.state.showLoading ? <AjaxLoading/> : null}
                <h1>
                    <div className="col-xs-12 col-sm-9 col-md-9 col-lg-9">
                        {this.state.order.title}
                    </div>
                    <div className="col-xs-12 col-xs-3 col-md-3 col-lg-3">
                        <Label bsStyle="success">{this.state.order.orderStatus}</Label>
                    </div>
                </h1>
                <div className="order col-xs-12 col-sm-12 col-md-12 col-lg-12">
                    <div className="page-hr"/>
                    <div className="order col-xs-12 col-sm-12 col-md-12 col-lg-12 order-description">
                        <h2>Description de ma commande</h2>
                        <div dangerouslySetInnerHTML={{__html: this.state.order.description}}/>
                    </div>
                    <div className="order col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <a href={this.state.order.specificationsLink} target="_blank">Cahier des charges de ma
                            commande</a>
                    </div>
                    <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                        <h2>Mon budget</h2>
                        <h3>{this.state.order.budget} €</h3>
                    </div>
                    <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                        <h2>Prix estimé par Denoria</h2>
                        {
                            this.state.order.price === 0
                                ?
                                <Alert bsStyle="info">
                                    <p>Nous n'avons pas encore fixé de prix pour votre commande.</p>
                                </Alert>
                                :
                                <h3 className="text-info">{this.state.order.price} € <small>(Le prix affiché inclut les frais paypal)</small></h3>}
                    </div>
                    <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                        <h2>Type de commande</h2>
                        <h3><Label bsStyle="info">{this.state.order.orderType}</Label></h3>
                    </div>
                    <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                        <h2>Status du paiement de ma commande</h2>
                        <h3><Label bsStyle="warning">{this.state.order.paymentStatus}</Label></h3>
                    </div>

                    <div className="payment-section col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        {
                            this.state.order.price > 0
                                ?
                                this.state.order.paymentStatus === "En attente"
                                    ?
                                    <OverlayTrigger trigger={['hover']} placement="top" overlay={popoverPaymentSection}>
                                        <div>
                                            <h2>Paiement de ma commande</h2>
                                            <p className="text-danger">TODO SYSTEME DE PAIEMENT PAR UN COMPONENT DEDIE/!\</p>
                                        </div>
                                    </OverlayTrigger>
                                    :
                                        <Alert bsStyle="success">
                                            <p>Vous avez payé votre commande. Merci de nous avoir fait confiance pour réaliser votre demande <i className="fa fa-heart made-with-love" aria-hidden="true"/> .</p>
                                        </Alert>
                                :
                                <div>
                                    <h2>Paiement de ma commande</h2>
                                    <Alert bsStyle="warning">
                                        <p>En attente d'un prix fixé par l'équipe en charge de votre commande.</p>
                                    </Alert>
                                </div>
                        }
                    </div>
                </div>
            </div>
        );
    }
}

export default Order;