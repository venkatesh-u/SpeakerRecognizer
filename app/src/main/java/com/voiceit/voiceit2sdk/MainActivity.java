package com.voiceit.voiceit2sdk;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;

import com.voiceit.voiceit2.VoiceItAPI2;

public class MainActivity extends AppCompatActivity {

    private VoiceItAPI2 myVoiceIt;
    private String [] userId = {"usr_edf80615c43a45369fc041a6a012a4e8", "usr_b932598f490f4b62b58c52523e35abaf"};
    private int userIdIndex = 0;
    private String groupId = "grp_19d5d3fe5c9046818a012e70d61df975";
    private String phrase = "Never forget tomorrow is a new day";
    private String contentLanguage = "en-US";
    private boolean doLivenessCheck = false; // Liveness detection is not used for enrollment views

    private Switch userIdSwitch;
    private Switch livenessSwitch;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If using user tokens, replace API_KEY below with the user token,
        // and leave the second argument as an empty string
        myVoiceIt = new VoiceItAPI2(
                "key_d353fc22636f4c5788abd20f1cce9317","tok_49b55c82eebd44e7b43d6d1a41fa9160");

        userIdSwitch = findViewById(R.id.switch_user);
        livenessSwitch = findViewById(R.id.switch_liveness);
        userIdSwitch.setText("User 1");
    }

    public void toggleLiveness(View view) {
        doLivenessCheck = livenessSwitch.isChecked();
    }

    public void toggleUser(View view) {
        if(userIdIndex == 0) {
            userIdIndex = 1;
            userIdSwitch.setText("User 2 ");
        } else {
            userIdIndex = 0;
            userIdSwitch.setText("User 1 ");
        }
    }

    public void displayIdentifiedUser(JSONObject response) {
        try {
            String id = response.getString("userId");
            if(userId[0].equals(id)) {
                Toast.makeText(mContext, "User 1 Identified", Toast.LENGTH_LONG).show();
            } else if (userId[1].equals(id)) {
                Toast.makeText(mContext, "User 2 Identified", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            System.out.println("JSONException: " + e.getMessage());
        }
    }

    public void encapsulatedVoiceEnrollment(View view) {
        myVoiceIt.encapsulatedVoiceEnrollment(this, userId[userIdIndex], contentLanguage, phrase, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedVoiceEnrollment onSuccess Result : " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                checkResponse(errorResponse);
                if (errorResponse != null) {
                    System.out.println("encapsulatedVoiceEnrollment onFailure Result : " + errorResponse.toString());
                }
            }
        });
    }

    public void encapsulatedVoiceVerification(View view) {
        myVoiceIt.encapsulatedVoiceVerification(this, userId[userIdIndex], contentLanguage, phrase, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedVoiceVerification onSuccess Result : " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                checkResponse(errorResponse);
                if (errorResponse != null) {
                    System.out.println("encapsulatedVoiceVerification onFailure Result : " + errorResponse.toString());
                }
            }
        });
    }

    public void encapsulatedVoiceIdentification(View view) {
       /* myVoiceIt.createGroup("Sample Group Description",new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("rrrrrrrrr",response.toString());
                System.out.println("encapsulatedVoiceIdentification onSuccess Result : " + response.toString());
                displayIdentifiedUser(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                checkResponse(errorResponse);
                if (errorResponse != null) {
                    System.out.println("encapsulatedVoiceIdentification onFailure Result : " + errorResponse.toString());
                }
            }
        });
        myVoiceIt.addUserToGroup("grp_19d5d3fe5c9046818a012e70d61df975", userId[0], new JsonHttpResponseHandler() {});
        myVoiceIt.addUserToGroup("grp_19d5d3fe5c9046818a012e70d61df975", userId[0], new JsonHttpResponseHandler() {});*/
        myVoiceIt.encapsulatedVoiceIdentification(this, groupId, contentLanguage, phrase, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedVoiceIdentification onSuccess Result : " + response.toString());
                displayIdentifiedUser(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                checkResponse(errorResponse);
                if (errorResponse != null) {
                    System.out.println("encapsulatedVoiceIdentification onFailure Result : " + errorResponse.toString());
                }
            }
        });
    }

    public void encapsulatedVideoEnrollment(View view) {
        myVoiceIt.encapsulatedVideoEnrollment(this, userId[userIdIndex], contentLanguage, phrase, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedVideoEnrollment onSuccess Result : " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                checkResponse(errorResponse);
                if (errorResponse != null) {
                    System.out.println("encapsulatedVideoEnrollment onFailure Result : " + errorResponse.toString());
                }
            }
        });
    }

    public void encapsulatedVideoVerification(View view) {
        myVoiceIt.encapsulatedVideoVerification(this, userId[userIdIndex], contentLanguage, phrase, doLivenessCheck, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedVideoVerification onSuccess Result : " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                checkResponse(errorResponse);
                if (errorResponse != null) {
                    System.out.println("encapsulatedVideoVerification onFailure Result : " + errorResponse.toString());
                }
            }
        });
    }

    public void encapsulatedVideoIdentification(View view) {
        myVoiceIt.encapsulatedVideoIdentification(this, groupId, contentLanguage, phrase, doLivenessCheck, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedVideoIdentification onSuccess Result : " + response.toString());
                displayIdentifiedUser(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                checkResponse(errorResponse);
                if (errorResponse != null) {
                    System.out.println("encapsulatedVideoIdentification onFailure Result : " + errorResponse.toString());
                }
            }
        });
    }

    public void encapsulatedFaceEnrollment(View view) {
        myVoiceIt.encapsulatedFaceEnrollment(this, userId[userIdIndex], new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedFaceEnrollment onSuccess Result : " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                checkResponse(errorResponse);
                if (errorResponse != null) {
                    System.out.println("encapsulatedFaceEnrollment onFailure Result : " + errorResponse.toString());
                }
            }
        });
    }

    public void encapsulatedFaceVerification(View view) {
        myVoiceIt.encapsulatedFaceVerification(this, userId[userIdIndex], doLivenessCheck, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedFaceVerification onSuccess Result : " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                checkResponse(errorResponse);
                if (errorResponse != null) {
                    System.out.println("encapsulatedFaceVerification onFailure Result : " + errorResponse.toString());
                }
            }
        });
    }

    public void encapsulatedFaceIdentification(View view) {
        myVoiceIt.encapsulatedFaceIdentification(this, groupId, doLivenessCheck, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                System.out.println("encapsulatedFaceIdentification onSuccess Result : " + response.toString());
                displayIdentifiedUser(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                checkResponse(errorResponse);
                if (errorResponse != null) {
                    System.out.println("encapsulatedFaceIdentification onFailure Result : " + errorResponse.toString());
                }
            }
        });
    }

    public void checkResponse(JSONObject response) {
        try {
            if (response.getString("responseCode").equals("IFVD")
                    || response.getString("responseCode").equals("ACLR")
                    || response.getString("responseCode").equals("IFAD")
                    || response.getString("responseCode").equals("SRNR")
                    || response.getString("responseCode").equals("UNFD")
                    || response.getString("responseCode").equals("MISP")
                    || response.getString("responseCode").equals("DAID")
                    || response.getString("responseCode").equals("UNAC")
                    || response.getString("responseCode").equals("CLNE")
                    || response.getString("responseCode").equals("INCP")
                    || response.getString("responseCode").equals("NPFC")) {
                Toast.makeText(this, "responseCode: " + response.getString("responseCode")
                         + ", " + getString(com.voiceit.voiceit2.R.string.CHECK_CODE), Toast.LENGTH_LONG).show();
                Log.e("MainActivity","responseCode: " + response.getString("responseCode")
                        + ", " + getString(com.voiceit.voiceit2.R.string.CHECK_CODE));
            }
        } catch (JSONException e) {
            Log.d("MainActivity","JSON exception : " + e.toString());
        }
    }

}
