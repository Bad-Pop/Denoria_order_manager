import React, {Component} from 'react';
import '../../assets/css/Footer.css';
import ScrollToTop from 'react-scroll-up';
import {goToTopStyle} from "../../../configuration/Configuration";

class Footer extends Component {

    render() {
        return (
            <div className="copyright footer navbar-fixed-bottom">
                <div className="container">
                    <div className="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <p>Denoria.fr © 2015 - 2017 - Tous droits réservés.</p>
                    </div>
                    <div className="col-lg-8 col-md-8 col-sm-12 col-xs-12">
                        <p className="text-right">Développé avec <i className="fa fa-heart made-with-love" aria-hidden="true"/> par <a href="https://alexisvachard.fr" target="_blank" rel="noopener noreferrer">Alexis VACHARD</a></p>
                    </div>
                    <ScrollToTop showUnder={160} style={goToTopStyle}>
                        <span><i className="fa fa-arrow-circle-up fa-2x" aria-hidden="true"/></span>
                    </ScrollToTop>
                </div>
            </div>
        );
    }
}

export default Footer;