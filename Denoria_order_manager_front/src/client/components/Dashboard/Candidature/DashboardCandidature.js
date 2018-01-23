import React, {Component} from 'react';
import { Alert } from 'react-bootstrap';

class DashboardCandidature extends Component{

    render(){
        return(
            <div className="col-xs-12 col-sm-12 col-md-9 col-lg-9">
                <h3>Ma candidature</h3>
                <hr className="page-hr"/>
                {
                    this.props.candidature.name
                    ?
                        <div>
                            <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3>Depuis combien de temps je joue à minecraft</h3>
                                <hr className="page-hr"/>
                                <p>{this.props.candidature.howLongPlayMinecraft}</p>
                            </div>
                            <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3>Combien de temps je suis disponible par semaine</h3>
                                <hr className="page-hr"/>
                                <p>{this.props.candidature.hoursPerWeek}</p>
                            </div>
                            <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3>Le rôle pour lequel je postule</h3>
                                <hr className="page-hr"/>
                                <p>{this.props.candidature.desiredRole}</p>
                            </div>
                            <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3>Ma description personnelle</h3>
                                <hr className="page-hr"/>
                                <div dangerouslySetInnerHTML={{__html: this.props.candidature.personnalDescription}}/>
                            </div>
                            <div className="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <h3>Pourquoi postuler chez Denoria</h3>
                                <hr className="page-hr"/>
                                <div dangerouslySetInnerHTML={{__html: this.props.candidature.why}}/>
                            </div>
                        </div>
                    :
                        <Alert>
                            <p>Vous n'avez pas encore postulé dans l'une de nos équipe.</p>
                        </Alert>
                }
            </div>
        );
    }
}
export default DashboardCandidature;