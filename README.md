# SocialMention
Test AppGate Repo

To Test in Postman or curl just run:

curl -X POST "http://localhost:8080/AnalyzeSocialMentionRefactor" \
     -H "Content-Type: application/json" \
     -d '{
          "message": "Test post",
          "facebookAccount": "user123",
          "tweeterAccount": "user_twitter",
          "tweeterUrl": "https://twitter.com/post/1",
          "facebookComments": ["Nice post!", "I disagree."]
         }'

         
Expected Output: HIGH_RISK
