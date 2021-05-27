import React, {Component} from 'react';
import {Link, withRouter} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from './AppNavbar';

class priceEdit extends Component {
    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    emptyItem = {
        name: "",
        price: "",
        description: "",
        nameError: "",
        priceError: "",
        descriptionError: ""
    };

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const price = await (await fetch(`/prices/${this.props.match.params.id}`)).json();
            this.setState({item: price});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
        console.log(this.state.item);
    }
    validate = () => {
        let nameError = "";
        let priceError = "";
        let descriptionError = "";

        if(!this.state.item.name) {
           nameError = "Name can't be empty";
        }
        if (!this.state.item.price)
        {
            priceError="Price can't be empty"
        }

        if (isNaN(this.state.item.price)&&this.state.item.price) {
                priceError = "Value is Not Number";
            }

       if(this.state.item.description) {
           if (!isNaN(this.state.item.description)) {
               descriptionError = "Description can't be just a Number";
           }
       }


        if (priceError || nameError||descriptionError) {
            this.setState({ priceError, nameError ,descriptionError});
            return false;
        }

        return true;
    };

    async handleSubmit(event) {
        event.preventDefault();
        console.log(this.state);
        const isValid = this.validate();
        if (isValid) {


            const {item} = this.state;

            await fetch('/prices' + (item.id ? '/' + item.id : ''), {
                method: (item.id) ? 'PUT' : 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(item),
            });
            this.props.history.push('/prices');
        }
    }

    render() {
        const {item} = this.state;
        const title = <h2>{item.id ? 'Edit Price' : 'Add Price'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                <br/>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="name">Name</Label>
                        <Input type="text" name="name" id="name" value={item.name || ''}
                               onChange={(event) => this.handleChange(event)} autoComplete="name"/>
                        <div style={{fontSize: 12, color: "red"}}>{this.state.nameError}</div>
                    </FormGroup>
                    <FormGroup>
                        <Label for="price">Price</Label>
                        <Input type="text" name="price" id="price" value={item.price || ''}
                               onChange={this.handleChange} autoComplete="price"/>
                        <div style={{fontSize: 12, color: "red"}}>{this.state.priceError}</div>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">description</Label>
                        <Input type="text" name="description" id="description" value={item.description || ''}
                               onChange={this.handleChange} autoComplete="description"/>
                        <div style={{fontSize: 12, color: "red"}}>{this.state.descriptionError}</div>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/prices">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(priceEdit);