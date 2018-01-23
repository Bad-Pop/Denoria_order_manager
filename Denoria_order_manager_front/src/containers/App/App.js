import React, {Component} from 'react';
import {BrowserRouter, Switch, Route, Redirect} from 'react-router-dom';
import Login from "../../client/components/Login/Login"
import Register from '../../client/components/Register/Register';
import Header from '../../client/views/header/Header';
import Footer from '../../client/views/footer/Footer';
import Dashboard from '../../client/components/Dashboard/Dashboard';
import NotFound from '../../client/components/NotFound/NotFound';
import Profil from '../../client/components/Account/Profil';
import Order from '../../client/components/Orders/Order';
import CreateOrder from '../../client/components/Orders/CreateOrder';
import CreateCandidature from '../../client/components/Candidature/CreateCandidature';
import {maintenanceMode} from "../../configuration/Configuration";
import Logo from '../../assets/images/maintenance.png';

import '../../assets/css/denoria.css';
import '../../assets/css/bootstrap.min.css';
import '../../assets/css/font-awesome.min.css';
import '../../assets/fonts/fontawesome-webfont.ttf';

class App extends Component {

    /**
     * Check if current user is logged
     * @returns {boolean}
     */
    requiredAuth(){
        const userPseudo = localStorage.getItem("user-pseudo");
        const sessionToken = localStorage.getItem("session-token");
        const isUser = localStorage.getItem("z-user");

        if(!userPseudo || !sessionToken || !isUser){
            return false;
        } else {
            return true;
        }
    }

    render() {

        if(maintenanceMode){
            return (
                <div className="container">
                    <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <h1>Maintenance en cours ...</h1>
                        <p>Notre interface client est actuellement en maintenance. Nous vous prions de bien vouloir nous excuser pour la gêne occasionnée.</p>
                        <img src={Logo} alt="logo" className="center-block img-responsive"/>
                    </div>
                </div>
            );
        } else {
            return (
                <BrowserRouter>
                    <div>
                        {
                            this.requiredAuth()
                                ?
                                <div>
                                    <Header/>
                                    <div className="container page-container">
                                        <Switch>
                                            <Route exact path="/" component={Dashboard}/>
                                            <Route exact path="/profil" component={Profil}/>
                                            <Route exact path="/profil/:userPseudo/:id" component={Order}/>
                                            <Route exact path="/nouvelle_commande" component={CreateOrder}/>
                                            <Route exact path="/nouvelle_candidature" component={CreateCandidature}/>
                                            <Route component={NotFound}/>
                                        </Switch>
                                    </div>
                                    <Footer/>
                                </div>
                                :
                                <Switch>
                                    <Route exact path="/connexion" component={Login}/>
                                    <Route exact path="/inscription" component={Register}/>
                                    <Redirect from="/" to="/connexion"/>
                                </Switch>
                        }
                    </div>
                </BrowserRouter>
            );
        }
    }
}

export default App;
