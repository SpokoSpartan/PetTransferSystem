<template>
  <div>
    <h1>Transfer animal</h1>
    <el-row>
      <el-col :span="11">
        <div class="grid-content bg-purple">
          <el-form class="form-container" style="width: 70%" ref="form" label-position="left" label-width="90%;">
            <h2 style="margin-top: 5%;">Animal to be transferred:</h2>
            <h2>{{this.organization.fullName}}</h2>
          </el-form>

          <el-form class="form-container" style="width: 90%" ref="form" :model="form" :rules="rules" label-position="left"
                   label-width="140px">
            <br>
            <el-form-item class="form-field" prop="fullName" label="Organization name" :error="getErrorOrganizationName()">
              <el-input placeholder="Organization name"
                        v-model="form.fullName"/>
            </el-form-item>
            <el-form-item class="form-field" prop="email" label="Email" :error=getErrorForEmail()>
              <el-input placeholder="Email"
                        v-model="form.email"/>
            </el-form-item>
            <el-form-item class="form-field" prop="address" label="Address" :error="getErrorForAddress()">
              <el-input placeholder="Address"
                        v-model="form.address"/>
            </el-form-item>
            <el-form-item class="form-field" prop="phoneNumber" label="Phone number" :error="getErrorForPhoneNumber()">
              <el-input placeholder="Phone number"
                        v-model="form.phoneNumber"/>
            </el-form-item>
            <br>

            <el-button style="margin-bottom: 12px;" type="primary" @click="createOrganization()" round>Register</el-button>

            <br>
          </el-form>

          <el-form class="form-container" style="width: 90%" ref="form" :model="formReg" :rules="rulesReg"
                   label-position="left" label-width="90%;">
            <h2>Find user:</h2>
            <el-form-item class="form-field" prop="pattern" label="Pattern" id="login-username">

              <el-row>
                <el-col :span="17">
                  <el-input placeholder="User name or email"
                            @keydown.enter.native="findUsers"
                            v-model="formReg.pattern"/>
                </el-col>
                <el-col :span="2">
                  <el-button type="primary" @click="findUsers" round>Search</el-button>
                </el-col>
              </el-row>
            </el-form-item>
            <el-table
              :data="foundUser"
              style="width: 100%">
              <el-table-column
                label="Name"
                prop="fullName">
              </el-table-column>
              <el-table-column
                label="Email"
                prop="email">
              </el-table-column>
              <el-table-column
                label="Phone number"
                prop="phoneNumber">
              </el-table-column>
              <el-table-column
                label="Actions">
                <template slot-scope="scope">
                  <el-button
                    round
                    type="primary"
                    size="normal"
                    @click="addToOrganization(scope.$index)">Add
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <br>
          </el-form>

        </div>
      </el-col>
      <el-col :span="2">
        <div class="grid-content bg-purple-light">
          <img style="margin-top: 60%;" src="../assets/icons-arrow.png">
        </div>
      </el-col>

      <el-col :span="11">
        <div class="grid-content bg-purple-light">
          <el-form v-if="isInOrganization()" class="form-container" style="width: 90%" ref="form" :model="form"
                   :rules="rules" label-position="left" label-width="90%;">
            <h2>Users in organization:</h2>
            <el-table
              :data="users"
              style="width: 100%">
              <el-table-column
                label="Name"
                prop="fullName">
              </el-table-column>
              <el-table-column
                label="Email"
                prop="email">
              </el-table-column>
              <el-table-column
                label="Phone number"
                prop="phoneNumber">
              </el-table-column>
              <el-table-column
                label="Actions">
                <template slot-scope="scope">
                  <el-button
                    round
                    type="primary"
                    style="background-color: #fb8817; border-color: #fb8817;"
                    size="normal"
                    @click="removeFromOrganization(scope.$index)">Remove
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
            <br>
          </el-form>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
    import axios from "axios";
    import VueRouter from 'vue-router'
    import Vue from "vue";
    import {Organization} from "../models/Organization";

    Vue.use(VueRouter);

    export default {
        name: "Manage organization",
        hasOrganization: true,
        data() {
            return {
                organization: Organization,
                users: [],
                foundUser: [],
                formReg: {
                    pattern: ''
                },
                rulesReg: {
                    pattern: [
                        {required: true, message: 'Pattern is required', trigger: 'blur'}
                    ]
                },
                form: {
                    fullName: '',
                    email: '',
                    phoneNumber: '',
                    address: ''
                },
                rules: {
                    fullName: [
                        {required: true, message: 'Username is required.', trigger: 'blur'},
                    ],
                    email: [
                        {required: true, message: 'Email is required.', trigger: 'blur'}
                    ],
                    address: [
                        {required: true, message: 'Address is required', trigger: 'blur'}
                    ],
                    phoneNumber: [
                        {required: true, message: 'Phone number is required', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            findUsers() {
                const token = localStorage.getItem('access_token');
                if (token !== null) {
                    axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
                }
                axios.get(this.$APIURL + 'user/find/not-in-organization/' + this.formReg.pattern)
                    .then(response => {
                        this.foundUser = response.data;
                        console.log(response);
                    }).catch(e => {
                    this.errors.push(e)
                })
            },
            removeFromOrganization(userId) {
                const receivingUser = this.users[userId];
                this.$confirm('This will remove user from organization. User will not be able to manage animals in organization.', 'Warning', {
                    confirmButtonText: 'OK',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    const token = localStorage.getItem('access_token');
                    if (token !== null) {
                        axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
                    }
                    axios.post(this.$APIURL + 'user/organization/remove-user/' + receivingUser.id)
                        .then(response => {
                            this.users = this.users.filter(function (obj) {
                                return obj.id !== receivingUser.id;
                            });
                            this.foundUser.push(receivingUser);
                            this.$message({
                                type: 'success',
                                message: 'Removing completed'
                            });
                        }).catch(e => {
                        this.$message({
                            type: 'error',
                            message: 'An error occurred. Please reload this page'
                        });
                    })
                }).catch(e => {
                    this.$message({
                        type: 'info',
                        message: 'Removing canceled'
                    });
                });
            },
            addToOrganization(userId) {
                const receivingUser = this.foundUser[userId];
                const token = localStorage.getItem('access_token');
                if (token !== null) {
                    axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
                }
                axios.post(this.$APIURL + 'user/organization/add-user/' + receivingUser.id)
                    .then(response => {
                        this.users.push(receivingUser);
                        this.foundUser = this.foundUser.filter(function (obj) {
                            return obj.id !== receivingUser.id;
                        });
                        this.$message({
                            type: 'success',
                            message: 'Removing completed'
                        });
                    }).catch(e => {
                    this.$message({
                        type: 'error',
                        message: 'An error occurred. Please reload this page'
                    });
                });
            },
            getErrorOrganizationName() {
                if (this.form.fullName === '') {
                    return;
                }
                if (this.form.fullName.length > 5) {
                    return;
                }
                return 'Username must have at least 6 chars';
            },
            getErrorForEmail() {
                if (this.form.email === '') {
                    return;
                }
                let regex = new RegExp('(?:[a-z0-9!#$%&\'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&\'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\\])');
                if (this.form.email.match(regex) !== null) {
                    return;
                }
                return 'Email format is required';
            },
            getErrorForAddress() {
                if (this.form.address === '') {
                    return;
                }
                if (this.form.address.length > 5) {
                    return;
                }
                return 'Username must have at least 6 chars';
            },
            getErrorForPhoneNumber() {
                if (this.form.phoneNumber === '') {
                    return;
                }
                if (this.form.phoneNumber.length > 8) {
                    return;
                }
                return 'Phone number must have at least 9 chars';
            },
            isInOrganization() {
                return this.organization.id !== 0;
            },
            createOrganization() {
                const token = localStorage.getItem('access_token');
                if (token !== null) {
                    axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
                }
                axios.post(this.$APIURL + 'user/organization/create', this.form)
                    .then(response => {
                        this.$message({
                            type: 'success',
                            message: 'Organization creation completed'
                        });
                    }).catch(e => {
                    this.$message({
                        type: 'error',
                        message: 'An error occurred. Please reload this page'
                    });
                });
            }
        },
        created() {
            const token = localStorage.getItem('access_token');
            if (token !== null) {
                axios.defaults.headers.common['Authorization'] = 'Bearer ' + token
            }
            axios.get(this.$APIURL + 'user/organization')
                .then(response => {
                    this.organization = response.data;
                    console.log(response.data);
                }).catch(e => {
                this.errors.push(e)
            });

            axios.get(this.$APIURL + 'user/all-in/organization')
                .then(response => {
                    this.users = response.data;
                    console.log(response.data);
                }).catch(e => {
                this.errors.push(e)
            });
        },
    }
</script>

<style scoped>
  >>> input {
    border-radius: 30px;
    text-decoration-color: black;
    color: black;
    border: black 1px solid;
    width: 90%;
    margin: 0 auto;
    float: left;
  }

  .upload-fields {
    /*float: left;*/
    /*display: flex;*/
  }

  >>> textarea {
    border-radius: 30px;
    text-decoration-color: black;
    color: black;
    border: black 1px solid;
    width: 90%;
    margin: 0 auto;
    float: left;
  }

  .el-form-item {
    margin: 0 auto;
    width: 90%;
  }

  >>> ::placeholder {
    color: black;
  }

  .form-field {
    margin-top: 16px;
    margin-bottom: 16px;
  }

  .form-container {
    margin: 0 auto;
    width: 50%;
    border-radius: 5px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
  }
</style>
