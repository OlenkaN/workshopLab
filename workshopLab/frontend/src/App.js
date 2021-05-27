import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import priceList from "./pricesList";
import priceEdit from "./priceEdit";

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/prices' exact={true} component={priceList}/>
            <Route path='/prices/:id' component={priceEdit}/>
          </Switch>
        </Router>
    )
  }
}

export default App;