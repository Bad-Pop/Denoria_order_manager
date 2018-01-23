import React, {Component} from 'react';
import { Button } from 'react-bootstrap';

import '../../assets/css/NotFound.css';

class NotFound extends Component {

    componentDidMount(){
        document.body.className="not-found-body";
    }

    render() {
        return (
            <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                <div className="hidden-xs hidden-sm col-md-7 col-lg-7">
                </div>
                <div className="col-xs-12 col-sm-12 col-md-5 col-lg-5">
                    <h1>Houston on a un problème !</h1>
                    <hr className="page-hr"/>
                    <p>Les rouages du temps et de l'internet vous ont mené à nous. Malheureusement il semblerait que vous souhaitiez afficher une page qui n'existe pas.</p>
                    <p><a href="/"><Button bsStyle="danger">retourner à l'accueil</Button></a></p>
                </div>
            </div>
        );
    }
}

export default NotFound;