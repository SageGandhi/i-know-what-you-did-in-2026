- code first(existing code already developed) or design first. use https://swapi.info for star wars api.
- clone this [repository](https://github.com/eazybytes/yaml.git) and go through [yaml-zero-to-master](https://www.udemy.com/course/yaml-zero-to-master) udemy course. following code snippet will provide you a gist of all yaml concept.
```yaml
# example of multiple document in a single yaml document, think of multiple spring boot profile for all environment
# --- will mark starting of one document and ... this will mark ending of the document. 
# denotes a comment, we can put it at the begining of line or after a spacing of the content of that line
# we have to use load all document when parser read the files containing multiple document instead of single one
--- # first document start, this document will describe anchor(&), alias(*) and overriding(<<:) 

# kind of pointer and dereferencing the pointer in C, 
# anchor(&) is pointer address and alias(*) is getting value at that address
Routine: &anchor-to-be-aliased
  SleepTime: !!str 12:00
  StartDate: !!timestamp 2026-01-01 12:00:00
  Activities: # block style vs flow style
  - !!str reading books
  - !!str watching udemy courses
  - !!str listening podcast
Days:
- Sunday: *anchor-to-be-aliased
- Monday: *anchor-to-be-aliased
- Tuesday: *anchor-to-be-aliased
- Wednesday: *anchor-to-be-aliased
- Thursday: *anchor-to-be-aliased
- Friday: 
   <<: *anchor-to-be-aliased # will be overriding Activities for Friday
   Activities:
   - watching movies
   - collecting information from reels
- Saturday: *anchor-to-be-aliased
... # first document end
--- # second document start # complex keys and values, multiline keys 
? which is your favorite 
 sport in childhood
: Criket 
? | # although both of these keys are semantically same but not duplicate due to | sign may contain new line/whitespaces
 which is your favorite 
 sport in childhood
: Criket 
# also list as keys and values
? - Development
  - User Acceptance
  - Production
: - https://development.com
  - https://user.acceptance.com
  - https://production.com
... # second document end
--- # this will be third document flow style
applications: [frontend, backend, andriod, ios]
unique-cities: !!set # set(? instead of -), no duplicate
 ? Washington
 ? Delhi
 ? Pune
 ? Kolkata
capital: !!omap # order map each mapping having one key
- USA: !!str Washington
- United Kingdom: London
- India: Delhi
...
--- # literal block(|) and foldable block(>), + will keep whitespace/newline, - will remove.
lorem-ipsum-literal: |+
    Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since 1966, when designers at Letraset and James Mosley, the librarian at St Bride Printing Library in London, took a 1914 Cicero translation and scrambled it to make dummy text for Letraset's Body Type sheets. It has survived not only many decades, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised thanks to these sheets and more recently with desktop publishing software like Aldus PageMaker and Microsoft Word including versions of Lorem Ipsum.
 
lorem-ipsum-foldable: >-
    It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).
hexa-decimal: 0xabcdef
octal: 0o1234567
positive-infinity: .inf 
negative-infinity: -.inf
invalid-number: .nan
null-value: null
null-value-tilde: ~
null-value-blank:
single_quote_style_does_not_eval_escape_seq: '''text with : colon and quote'''
double_quote_style_eval_escape_seq: "First\nSecond\nThird"
...
```
```json
{
    "Routine": {
        "SleepTime": "12:00", "StartDate": "2026-01-01T12:00:00.000Z",
        "Activities": ["reading books", "watching udemy courses", "listening podcast"]
    },
    "Days": [
        {
            "Sunday": {
                "SleepTime": "12:00", "StartDate": "2026-01-01T12:00:00.000Z",
                "Activities": ["reading books", "watching udemy courses", "listening podcast"]
            }
        },
        {
            "Monday": {
                "SleepTime": "12:00", "StartDate": "2026-01-01T12:00:00.000Z",
                "Activities": ["reading books", "watching udemy courses", "listening podcast"]
            }
        },
        {
            "Tuesday": {
                "SleepTime": "12:00", "StartDate": "2026-01-01T12:00:00.000Z",
                "Activities": ["reading books", "watching udemy courses", "listening podcast"]
            }
        },
        {
            "Wednesday": {
                "SleepTime": "12:00", "StartDate": "2026-01-01T12:00:00.000Z",
                "Activities": ["reading books", "watching udemy courses", "listening podcast"]
            }
        },
        {
            "Thursday": {
                "SleepTime": "12:00", "StartDate": "2026-01-01T12:00:00.000Z",
                "Activities": ["reading books", "watching udemy courses", "listening podcast"]
            }
        },
        {
            "Friday": {
                "SleepTime": "12:00", "StartDate": "2026-01-01T12:00:00.000Z",
                "Activities": ["watching movies", "collecting information from reels"]
            }
        },
        {
            "Saturday": {
                "SleepTime": "12:00", "StartDate": "2026-01-01T12:00:00.000Z",
                "Activities": ["reading books", "watching udemy courses", "listening podcast"]
            }
        }
    ],
    "which is your favorite sport in childhood": "Criket",
    "which is your favorite \nsport in childhood\n": "Criket",
    "Development,User Acceptance,Production": [
        "https://development.com",
        "https://user.acceptance.com",
        "https://production.com"
    ],
    "applications": [
        "frontend",
        "backend",
        "andriod",
        "ios"
    ],
    "unique-cities": {
        "Washington": null,
        "Delhi": null,
        "Pune": null,
        "Kolkata": null
    },
    "capital": [{"USA": "Washington"},{"United Kingdom": "London"},{"India": "Delhi"}],
    "lorem-ipsum-literal": "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since 1966, when designers at Letraset and James Mosley, the librarian at St Bride Printing Library in London, took a 1914 Cicero translation and scrambled it to make dummy text for Letraset's Body Type sheets. It has survived not only many decades, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised thanks to these sheets and more recently with desktop publishing software like Aldus PageMaker and Microsoft Word including versions of Lorem Ipsum.\n\n",
    "lorem-ipsum-foldable": "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).",
    "hexa-decimal": 11259375,
    "octal": 342391,
    "positive-infinity": null,
    "negative-infinity": null,
    "invalid-number": null,
    "null-value": null,
    "null-value-tilde": null,
    "null-value-blank": null,
    "single_quote_style_does_not_eval_escape_seq": "'text with : colon and quote'",
    "double_quote_style_eval_escape_seq": "First\nSecond\nThird"
}
```