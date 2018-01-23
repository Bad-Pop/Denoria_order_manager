import React, {Component} from 'react';
import { NavItem, Nav, NavDropdown, MenuItem, Tooltip, OverlayTrigger } from 'react-bootstrap';

import '../../assets/css/Header.css';

class HeaderLinks extends Component{

    constructor(props){
        super(props);
        this.logout = this.logout.bind(this);
    }

    logout = e =>{
        localStorage.clear();
        window.location.href = "/connexion";
        window.location.replace("/connexion");
    };

    render(){
        const userPseudo = localStorage.getItem("user-pseudo");
        const avatarUrl = "http://cravatar.eu/helmavatar/" + userPseudo + "/64.png";
        const dashboardTooltip = (<Tooltip id="dashboardTooltip"><strong>Tableau de bord</strong></Tooltip>);
        const accountTooltip = (<Tooltip id="dashboardTooltip"><strong>{userPseudo}</strong></Tooltip>);
        const denoriaTooltip = (<Tooltip id="dashboardTooltip"><strong>DENORIA.FR</strong></Tooltip>);
        const newOrderTooltip = (<Tooltip id="dashboardTooltip"><strong>Nouvelle commande</strong></Tooltip>);
        const newCandidatureTooltip = (<Tooltip id="dashboardTooltip"><strong>Nouvelle candidature</strong></Tooltip>);

        return (
            <div>
                <Nav>
                    <OverlayTrigger placement="bottom" overlay={denoriaTooltip}>
                        <NavItem href="https://denoria.fr" target="_blank" className="hidden-xs">
                            <i className="fa fa-globe  hidden-xs"/>
                            <p className="hidden-lg hidden-md hidden-sm">Denoria.fr</p>
                        </NavItem>
                    </OverlayTrigger>
                    <OverlayTrigger placement="bottom" overlay={dashboardTooltip}>
                        <NavItem href="/">
                            <i className="fa fa-dashboard hidden-xs"/>
                            <p className="hidden-lg hidden-md hidden-sm">Dashboard</p>
                        </NavItem>
                    </OverlayTrigger>
                    <OverlayTrigger placement="bottom" overlay={newOrderTooltip}>
                        <NavItem href="/nouvelle_commande">
                            <i className="fa fa-cart-plus hidden-xs"/>
                            <p className="hidden-lg hidden-md hidden-sm">Nouvelle commande</p>
                        </NavItem>
                    </OverlayTrigger>
                    <OverlayTrigger placement="bottom" overlay={newCandidatureTooltip}>
                        <NavItem href="/nouvelle_candidature">
                            <i className="fa fa-plus hidden-xs"/>
                            <p className="hidden-lg hidden-md hidden-sm">Nouvelle candidature</p>
                        </NavItem>
                    </OverlayTrigger>
                </Nav>
                <Nav pullRight>
                    <NavDropdown title={
                        <OverlayTrigger placement="left" overlay={accountTooltip}>
                            <span>
                                <img src={avatarUrl} alt={userPseudo} className="user-avatar"/>
                            </span>
                        </OverlayTrigger>
                    } id="basic-nav-dropdown-right">
                        <MenuItem href="/profil"><p><i className="fa fa-user"/> Mon profil</p></MenuItem>
                        {/*<MenuItem eventKey={2.2} href="/profil"><p><i className="fa fa-lock"/> Mot de passe</p></MenuItem>*/}
                        <MenuItem onSelect={e => this.logout(e)}><p><i className="fa fa-sign-out"/> Déconnexion</p></MenuItem>
                    </NavDropdown>
                    <NavItem href="https://www.youtube.com/c/DenoriaTeamBuild/?sub_confirmation=1" target="_blank"
                             className="hidden-xs">
                        <i className="fa fa-youtube-play"/>
                    </NavItem>
                    <NavItem href="https://twitter.com/TeamDenoria" target="_blank"
                             className="hidden-xs twitter-header">
                        <i className="fa fa-twitter"/>
                    </NavItem>
                    <NavItem href="https://www.facebook.com/denoriaBuildTeam/" target="_blank"
                             className="hidden-xs facebook-header">
                        <i className="fa fa-facebook"/>
                    </NavItem>
                </Nav>
            </div>
        );
    }
}

export default HeaderLinks;
